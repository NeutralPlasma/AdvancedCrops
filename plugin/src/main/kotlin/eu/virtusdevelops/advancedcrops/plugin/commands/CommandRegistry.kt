package eu.virtusdevelops.advancedcrops.plugin.commands

import eu.virtusdevelops.advancedcrops.plugin.AdvancedCrops
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.command.CommandSender
import org.incendo.cloud.CommandManager
import org.incendo.cloud.annotations.AnnotationParser
import org.incendo.cloud.paper.util.sender.Source

class CommandRegistry(private val plugin: AdvancedCrops, private val manager: CommandManager<Source>) {

    private val annotationParser: AnnotationParser<Source> = AnnotationParser(manager, Source::class.java)


    val COMMANDS: List<AbstractCommand> = listOf(
        ReloadCommand(),
        HelpCommand()
    )


    init {
        registerCommands()
    }


    private fun registerCommands() {

        COMMANDS.forEach {
            command -> command.registerCommand(plugin, annotationParser)
        }

    }

}