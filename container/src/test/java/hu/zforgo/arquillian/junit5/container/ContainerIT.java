package hu.zforgo.arquillian.junit5.container;

import io.github.zforgo.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ArquillianExtension.class)
class ContainerIT {

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "containertest.war")
				.addClass(FooService.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	FooService fooService;

	@Test
	void bootstrapTest() {
		assertEquals("foo", fooService.foo());
	}

	@ParameterizedTest
	@ValueSource(ints = {2, 4, 10, 20})
	void evenTests(int input) {
		assertEquals(FooService.EvenOdd.EVEN, fooService.evenOdd(input));
	}
}
