## Welcome to JDT Extension

The motivation is to make Eclipse JDT more powerful and easy-to-use.

### Guava-based hashCode(), equals() and toString()

Eclipse provides utilities to generate hashCode(), equals() and toString(). But it's the old-style. The generated functions are usually very long, which makes the code hard to read.

Google Guava provides a better solution for it. See [here](https://github.com/google/guava/wiki/CommonObjectUtilitiesExplained) for more details.

JDT Extension can generate Guava-styled functions, which are shorter, tidy and easy to read.

#### Usage

* Right-click on a Java class

![screen1](https://bloodlee.github.io/img/jdt-extension-screen5.png)

* Select the item to generate hashCode()/equals() or toString()
* Select the classes in pop out dialog and click "OK"

![screen2](https://bloodlee.github.io/img/jdt-extension-screen3.png)

* Following is a sample

```java
import com.google.common.base.Objects;

public class A {

	private int a;
	
	private double b;
	
	private String c;

	@Override
	public int hashCode() {
	  return Objects.hashCode(a, b, c);
	}

	@Override
	public boolean equals(Object obj) {
	  if (obj == this) {
	    return true;
	  }
	  
	  if (!(obj instanceof A)) {
	      return false;
	  }
	  
	  A anotherInstance = (A) obj;
	  return Objects.equal(anotherInstance.a, this.a)
	      && Objects.equal(anotherInstance.b, this.b)
	      && Objects.equal(anotherInstance.c, this.c);
	}
	
}

```

### Search by Google, StackOverflow

Another utility is to put Google/StackOverflow search in context menu in editors/consoles/terminals.

#### Usage

* Select the text you want to search in editors/consoles/terminals.
* Right click and select "Search with Google" or "Search with StackOverflow"

![screen3](https://bloodlee.github.io/img/jdt-extension-screen4.png)

### Installation

The simplest way is to Eclipse Marketplace.

Open Eclipse Marketplace, search "JDT Extension" and click install.

![marketplace](https://bloodlee.github.io/img/marketplace_installation.png)

You can also use update site URL: https://bloodlee.github.io/jdt_extension_update/
