import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class TestMain {
    @Disabled
    @Test
    @Timeout(value = 22)
    public void timeOut() throws Exception {
        Main.main(null);
    }
}
