package hu.zforgo.arquillian.junit5.container;

import javax.ejb.Stateless;

@Stateless
public class FooService {
	public static enum EvenOdd {
		EVEN, ODD;
	}

	public String foo() {
		return "foo";
	}

	public EvenOdd evenOdd(int value) {
		return EvenOdd.values()[value % 2];
	}
}
