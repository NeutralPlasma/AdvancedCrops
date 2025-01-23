package eu.virtusdevelops.advancedcrops.plugin.commands

import eu.virtusdevelops.advancedcrops.plugin.AdvancedCrops
import eu.virtusdevelops.virtuscore.chat.TextUtils
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.command.CommandSender
import org.incendo.cloud.annotations.AnnotationParser
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.CommandDescription
import org.incendo.cloud.annotations.Permission
import org.incendo.cloud.paper.util.sender.Source

class ReloadCommand : AbstractCommand {
    override fun registerCommand(plugin: AdvancedCrops, annotationParser: AnnotationParser<Source>) {
        annotationParser.parse(this)
    }


    @Permission("advancedcrops.reload")
    @Command("crops reload")
    @CommandDescription("Reload all the configuration files")
    fun reloadCommand(
        ctx: Source
    ){
        val sender: CommandSender = ctx.source()

        sender.sendMessage(TextUtils.parse("""
            <gray>---------------</gray>
            <green>Reloaded</green>
            <gray>---------------
        """.trimIndent()
        ))
    }

}