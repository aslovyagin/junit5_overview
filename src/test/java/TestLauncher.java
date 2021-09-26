import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TagFilter;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

public class TestLauncher {
    public static void main(String[] args) {
        Launcher launcher = LauncherFactory.create(); // creating customer launcher

        SummaryGeneratingListener summaryGeneratingListener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(summaryGeneratingListener); // info about tests

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
                .request()
                .selectors(DiscoverySelectors.selectClass(UserServiceTest.class)) //which classes will be executed
                .filters(TagFilter.includeTags("tag1"))  //which tags will be executed
                .build();

        launcher.execute(request);
        summaryGeneratingListener.getSummary().printTo(new PrintWriter(System.out));
    }
}
