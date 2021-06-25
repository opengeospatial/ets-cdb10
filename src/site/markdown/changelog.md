
# Release Notes

## Version 1.0 (2021-06-25)

- [#54](https://github.com/opengeospatial/ets-cdb10/issues/54): Prepare release 1.0
- [#50](https://github.com/opengeospatial/ets-cdb10/issues/50): Add template to get an XML/JSON response via rest endpoint
- [#52](https://github.com/opengeospatial/ets-cdb10/pull/52): Set Docker TEAM Engine version to 5.4.1

## Version 0.6 (2020-12-16)

- [#39](https://github.com/opengeospatial/ets-cdb10/issues/39): Configure the CDB Structure conformance class to be the minimal required for certification
- [#40](https://github.com/opengeospatial/ets-cdb10/issues/40): Configure Docker Maven Plugin
- [#42](https://github.com/opengeospatial/ets-cdb10/issues/42): Test suite breaks REST interface of TEAM Engine
- [#41](https://github.com/opengeospatial/ets-cdb10/issues/41): Update dependency schema-utils to version 1.8

## Version 0.5 (2020-11-16)

- Fix parsing of coarse-level LODs in filenames
- Relax validation of Model Texture Component Selectors
- Improve error messages for XML syntax/schema violations
    + (Closes [#19](https://github.com/opengeospatial/ets-cdb10/issues/19))
- Allow optional Metadata files to be omitted and still pass the test suite
- Include test suite version in TestNG output
- Allow testing an archived CDB specified by a remote URL
    + (Closes [#7](https://github.com/opengeospatial/ets-cdb10/issues/7))
- Add test annotations for referencing the specification sections
    + (Closes [#20](https://github.com/opengeospatial/ets-cdb10/issues/20))
- Improve installation instructions
    + (Closes [#27](https://github.com/opengeospatial/ets-cdb10/issues/27))
- Embed CDB reference data into the deployed archive for usage with TEAM Engine or JAR execution
- Fix bug where local file paths would fail the test suite
    + (Closes [#26](https://github.com/opengeospatial/ets-cdb10/issues/26))
- Allow loading of remote schema for Geomatics Attributes and Vendor Attributes Metadata files
    + (Closes [#22](https://github.com/opengeospatial/ets-cdb10/issues/22))
- Allow presence of `ExtMetadata` directory without raising an error
    + (Closes [#13](https://github.com/opengeospatial/ets-cdb10/issues/13))
- Add support for validation GSModel archives
    + (Closes [#6](https://github.com/opengeospatial/ets-cdb10/issues/6))
- Update tests to produce proper "skip" messages for untested cases
- Clean up Java dependencies
    + (Closes [#34](https://github.com/opengeospatial/ets-cdb10/issues/34))
- Bump `xercesImpl` from `2.11.0` to `2.12.0`
- Temporarily removed GSModel tests
    + (to be re-enabled for a later test suite release)

## Version 0.4 (2019-05-01)

- Fix javadoc error under JDK 12
- Update Maven plugins to support JDK 12
- Move documentation to site documentation instead of root of repository
- Add documentation on self testing and JDK support
- Fix typo on 400 dataset
- Update sample CDB used to test TestNG methods
- Allow for other files in datasets aside from primary dataset
- Fix false error on LOD directories in 502 dataset
- Fix false errors on filenames with variable number of underscores
- Replace sample CDB Metadata with OGC CDB 1.0 metadata (replaces CDB 3.2 metadata)
- Use embedded Datasets.xml file instead of metadata from CDB under test
- Reorganize XML utility classes
- Fix false errors on Version.xml metadata
- Update test fixtures with new OGC CDB 1.0 metadata
- Remove unused HTTP client code (part of default ETS)
- Extract validation methods for structure tests into re-usable classes
- Move all filename regular expressions to single class
- Partially support OGC CDB 1.1 for version tests
- Support extended datasets (9xx)
- Add reference XML for Component Selectors
- Add tests to validate Component Selectors against datasets

## Version 0.3 (2019-01-09)

- Only test directories that exist in CDB under test
- Improve web form for TEAM Engine integration
- Add guide for TEAM Engine, Eclipse deployment
- Fix broken testNG javadoc URL
- Fix build on Windows platform
- [Add tests for GTModel sub-type directories and files](https://github.com/opengeospatial/ets-cdb10/issues/1)

## Version 0.2 (2018-03-19)

- Create conformance level 2 tests for structure tests
- Include a sample CDB for JUnit tests
- Add tests for valid directory names
- Support different conformance level testing
- Add tests for valid file names
- Remove snapshot target builds from repository
- Add instructions for building from source

## Version 0.1 (2016-11-18)

The initial release implements the following test requirements:

- Create and implement Metadata tests
