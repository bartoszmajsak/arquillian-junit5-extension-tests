package hu.zforgo.arquillian.junit5.client;

import org.jboss.arquillian.junit5.ArquillianExtension;
import io.restassured.response.ResponseBody;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.MediaType;
import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ArquillianExtension.class)
public class ClientIT {

	@ArquillianResource
	private URL basePath;

	@Deployment(testable = false)
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "clienttests.war")
				.addClasses(JaxRsActivator.class, FooResource.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	public void injectSuccess() {
		Assertions.assertNotNull(basePath);
	}

	@Test
	public void valid() {
		ResponseBody response = given().log().all(true)
				.contentType(MediaType.TEXT_PLAIN)
				.accept(MediaType.MEDIA_TYPE_WILDCARD)
				.get(basePath)
		.then().extract().response().body();
		assertEquals("foo", response.asString());
	}
}
