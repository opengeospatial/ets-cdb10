
# Test suite: ets-cdb10

## Scope

This executable test suite (ETS) verifies the conformance of a CDB database (the
IUT) with respect to the [OGC CDB 1.0 specification][CDB-10]. The test suite 
currently validates the metadata, directory structure, and file naming against
the specification and will report on any exceptions to the specification.

[CDB-10]: https://www.opengeospatial.org/standards/cdb

**Figure 1: Conceptual Model**

![OGC CDB 1.0 Conceptual Model](img/conceptual-model.jpg)

Figure source: *OGC CDB 1.0 Volume 11: OGC CDB Core Standard Conceptual Model*

Several conformance classes are defined in the CDB 1.0 specification; the ones 
listed below are covered by this test suite:

* CDB Structure
    - `/conf/core/file-structure`
    - `/conf/core/gtmodel-naming`
    - `/conf/core/mmodel-naming`
    - `/conf/core/tiled-data`
    - `/conf/core/navdata-naming`
    - `/conf/core/nav-data`
    - `/conf/core/tiled-raster-datasets-general`
    - `/conf/core/tiled-raster-elevation-terrain`
    - `/conf/core/tiled-terrain-bathymetry`
* Metadata and Versioning
    - `/conf/core/metadata-version`
    - `/conf/core/metadata-datasets`

## Test requirements

The documents listed below stipulate requirements that must be satisfied by a 
conforming implementation.

1. [OGC CDB Core Standard: Model and Physical Data Store Structure 1.0](https://portal.opengeospatial.org/files/?artifact_id=72712)

Tests for various dataset naming and directory placements will be skipped if the
CDB (IUT) does not contain that dataset.

## Test suite structure

The test suite definition file (testng.xml) is located in the root package, 
`org.opengis.cite.cdb10`. A conformance class corresponds to a &lt;test&gt; element, each 
of which includes a set of test classes that contain the actual test methods. 
The general structure of the test suite is shown in Table 1.

<table>
  <caption>Table 1 - Test suite structure</caption>
  <thead>
    <tr style="text-align: left; background-color: LightCyan">
      <th>Conformance class</th>
      <th>Test classes</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>CDB Structure</td>
      <td>org.opengis.cite.cdb10.cdbStructure.*</td>
    </tr>
    <tr>
      <td>Metadata And Versioning</td>
      <td>org.opengis.cite.cdb10.metadataAndVersioning.*</td>
    </tr>
  </tbody>
</table>

The Javadoc documentation provides more detailed information about the test 
methods that constitute the suite.


## Test run arguments

The test run arguments are summarized in Table 2. The _Obligation_ descriptor can 
have the following values: M (mandatory), O (optional), or C (conditional).

<table>
	<caption>Table 2 - Test run arguments</caption>
	<thead>
    <tr>
      <th>Name</th>
      <th>Value domain</th>
	    <th>Obligation</th>
	    <th>Description</th>
    </tr>
  </thead>
	<tbody>
    <tr>
      <td>iut</td>
      <td>URI</td>
      <td>M</td>
      <td>A URI that refers to the implementation under test or metadata about it.
    Ampersand ('&amp;') characters must be percent-encoded as '%26'. This URI should 
    refer to a CDB on the file system that is accessible by this test suite tool, and
    not refer to a web URI. This also applies when running under TEAM Engine.</td>
    </tr>
	  <tr>
      <td>ics</td>
      <td>A comma-separated list of string values.</td>
      <td>O</td>
      <td>An implementation conformance statement that indicates which conformance 
      classes or options are supported. E.g. "1,2" would run both "CDB Structure" 
      tests and "Metadata and Versioning" tests.</td>
    </tr>
	</tbody>
</table>
