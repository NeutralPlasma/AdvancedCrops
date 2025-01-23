package eu.virtusdevelops.advancedcrops.plugin;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;

public class AdvancedCropsLoader implements PluginLoader {
    @Override
    public void classloader(PluginClasspathBuilder classpathBuilder) {
        var resolver = new MavenLibraryResolver();
        resolver.addRepository(new RemoteRepository.Builder("central", "default", "https://repo.maven.apache.org/maven2/").build());
        resolver.addDependency(new Dependency(new DefaultArtifact("com.h2database.h2:2.3.232"), null));
        classpathBuilder.addLibrary(resolver);
    }
}
