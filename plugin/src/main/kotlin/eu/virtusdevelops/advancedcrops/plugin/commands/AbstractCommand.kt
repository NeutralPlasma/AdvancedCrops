package eu.virtusdevelops.advancedcrops.plugin.commands

import eu.virtusdevelops.advancedcrops.plugin.AdvancedCrops
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.command.CommandSender
import org.incendo.cloud.annotations.AnnotationParser
import org.incendo.cloud.paper.util.sender.Source

interface AbstractCommand {
    fun registerCommand(
        plugin: AdvancedCrops,
        annotationParser: AnnotationParser<Source>
    )
}