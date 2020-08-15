package sung.demo.helloworld.single;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"tut1", "hello-world"})
@Configuration
public class TestConfig {

	@Bean
	public Queue hello() {
		return new Queue("hello");
	}
	
	@Profile("receiver")
	@Bean
	public TestReceiver receiver() {
		return new TestReceiver();
	}
	
	@Profile("sender")
	@Bean
	public TestSender sender() {
		return new TestSender();
	}
}
