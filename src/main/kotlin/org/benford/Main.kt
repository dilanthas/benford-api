package org.benford

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import kotlinx.coroutines.runBlocking
import org.benford.api.ApiServer
import org.benford.config.RestApiConfig
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.system.exitProcess

fun main(args: Array<String>) = runBlocking<Unit> {
    if (args.size != 1) {
        System.err.println("Illegal number of arguments. Usage <configFile>")
        exitProcess(1)
    }
    val configFile: Path = Paths.get(args[0])
    if(!Files.exists(configFile)){
        System.err.println("Config file $configFile does not exists")
        exitProcess(1)
    }
    loadConfig(configFile).also { config ->
        val server = ApiServer(config)
        server.start()
    }
}

private fun loadConfig(configFile: Path): RestApiConfig {
    return Yaml(configuration = YamlConfiguration(strictMode = false)).decodeFromString(
        deserializer = RestApiConfig.serializer(),
        string = Files.readString(configFile)
    )
}
