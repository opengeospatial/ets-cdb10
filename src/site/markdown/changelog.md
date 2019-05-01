
# Release Notes

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
