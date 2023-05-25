import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/features",
        glue = "ru.iq.test.page",
        plugin = {"pretty"},
        tags = {"@test"},
        // dryRun = false,
        strict = false,
        snippets = SnippetType.UNDERSCORE,
        monochrome = true
)
public class TestRunner {

}
