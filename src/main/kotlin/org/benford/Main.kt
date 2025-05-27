package org.benford

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import kotlinx.coroutines.runBlocking
import org.benford.api.ApiServer
import org.benford.config.RestApiConfig
import org.benford.service.BenfordService
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.system.exitProcess

fun main(args: Array<String>) = runBlocking<Unit> {
    startApp(args)
}

suspend fun startApp(args: Array<String>, exit: (Int) -> Nothing = { exitProcess(it) }) {
    if (args.size != 1) {
        System.err.println("Illegal number of arguments. Usage <configFile>")
        exit(1)
    }

    val configFile = Paths.get(args[0])
    if (!Files.exists(configFile)) {
        System.err.println("Config file $configFile does not exist")
        exit(1)
    }
    val config = loadConfig(configFile)
    ApiServer(config, BenfordService()).start()
}

private fun loadConfig(configFile: Path): RestApiConfig {
    return Yaml(configuration = YamlConfiguration(strictMode = false)).decodeFromString(
        deserializer = RestApiConfig.serializer(),
        string = Files.readString(configFile)
    )
}
