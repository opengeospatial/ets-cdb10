[![Build Status](https://travis-ci.org/pixeltree/ets-cdb10.svg)](https://travis-ci.org/pixeltree/ets-cdb10)

## Test suite: ets-cdb10

### Scope

This test suite validates the conformance level of a local CDB on the filesystem.

Visit the [project documentation website][docs] for more information, 
including the API documentation.

[docs]: http://opengeospatial.github.io/ets-cdb10/

### How to run the tests

There are several options for executing the test suite.

#### 1. OGC Test Harness

Use [TEAM Engine][TEAM Engine], the official OGC test harness. The latest test suite 
release are usually available at the [beta testing facility][Test Facility].
You can also [build and deploy][TEAM Engine Source] the test harness 
yourself and use a local installation.

[TEAM Engine]: https://github.com/opengeospatial/teamengine
[Test Facility]: http://cite.opengeospatial.org/te2/
[TEAM Engine Source]: https://github.com/opengeospatial/teamengine

#### 2. Integrated Development Environment (IDE)

Use a Java IDE such as Eclipse, NetBeans, or IntelliJ. Clone the repository and build the project.

Set the main class to run: `org.opengis.cite.cdb10.TestNGController`

Arguments: The first argument must refer to an XML properties file containing the 
required test run arguments. If not specified, the default location at `$
{user.home}/test-run-props.xml` will be used.
   
You can modify the sample file in `src/main/config/test-run-props.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties version="1.0">
  <comment>Test run arguments</comment>
  <entry key="iut">/path/to/CDB</entry>
  <entry key="ics">1,2</entry>
</properties>
```

The TestNG results file (`testng-results.xml`) will be written to a subdirectory
in `${user.home}/testng/` having a UUID value as its name.

#### 3. Command Shell (console)

One of the build artifacts is an "all-in-one" JAR file that includes the test 
suite and all of its dependencies; this makes it very easy to execute the test 
suite in a command shell:

`java -jar ets-cdb10-0.2-SNAPSHOT-aio.jar [-o|--outputDir $TMPDIR] [test-run-props.xml]`

### Building

This test suite is compatible with Apache Maven. You can build the test 
suite from a local repository:

```sh
$ mvn install
```

The compiled JAR will be available at `target/ets-cdb10-0.2-SNAPSHOT-aio.jar`.
For TEAM Engine, you will need `target/ets-cdb10-0.2-SNAPSHOT-ctl.zip` and `target/ets-cdb10-0.2-SNAPSHOT-deps.zip`.

### How to Contribute

If you would like to get involved, you can:

* [Report an issue](https://github.com/opengeospatial/ets-cat30/issues) such as a defect or 
an enhancement request
* Help to resolve an [open issue](https://github.com/opengeospatial/ets-cat30/issues?q=is%3Aopen)
* Fix a bug: Fork the repository, apply the fix, and create a pull request
* Add new tests: Fork the repository, implement and verify the tests on a new topic branch, 
and create a pull request (don't forget to periodically rebase long-lived branches so 
there are no extraneous conflicts)
