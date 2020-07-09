<?xml version="1.0" encoding="UTF-8"?>
<ctl:package xmlns:ctl="http://www.occamlab.com/ctl" xmlns:saxon="http://saxon.sf.net/" xmlns:tec="java:com.occamlab.te.TECore" xmlns:tng="java:org.opengis.cite.cdb10.TestNGController" xmlns:tns="http://www.opengis.net/cite/cdb10" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <ctl:function name="tns:run-ets-cdb10">
    <ctl:param name="testRunArgs">A Document node containing test run arguments (as XML properties).</ctl:param>
    <ctl:param name="outputDir">The directory in which the test results will be written.</ctl:param>
    <ctl:return>The test results as a Source object (root node).</ctl:return>
    <ctl:description>Runs the cdb10 ${version} test suite.</ctl:description>
    <ctl:code>
      <xsl:variable name="controller" select="tng:new($outputDir)"/>
      <xsl:copy-of select="tng:doTestRun($controller, $testRunArgs)"/>
    </ctl:code>
  </ctl:function>
  <ctl:suite name="tns:ets-cdb10-${version}">
    <ctl:title>Test suite for OGC CDB</ctl:title>
    <ctl:description>Checks implementations of the OGC CDB API for conformance against the candidate standard (OGC 15-113).
    </ctl:description>
    <ctl:starting-test>tns:Main</ctl:starting-test>
  </ctl:suite>
  <ctl:test name="tns:Main">
    <ctl:assertion>The test subject satisfies all applicable constraints.</ctl:assertion>
    <ctl:code>
      <xsl:variable name="form-data">
        <ctl:form height="600" method="POST" width="800" xmlns="http://www.w3.org/1999/xhtml">
          <h2>Test suite: ets-cdb10</h2>
          <div bgcolor="#F0F8FF" class="scope" style="background:#F0F8FF">
            <p>The CDB implementation under test (IUT) is checked against the 
              <a href="http://www.opengeospatial.org/projects/groups/cdbswg">OGC CDB 1.0</a></p>
            <p>Two conformance levels are defined in the specifications. Level 2 is based on Level 1.</p>
            <ol>
              <li>Level 1: CDB Structure</li>
              <li>Level 2: Metadata and Versioning</li>
            </ol>
            <p>Detailed information about the test suite is available 
              <a href="index.html" target="otherwindow">here</a>
              .</p>
            <p>Two conformance levels are defined.</p>
          </div>
          <fieldset style="background:#ccffff">
            <legend style="font-family: sans-serif; color: #000099; background-color:#F0F8FF; border-style: solid; border-width: medium; padding:4px">Implementation Under Test (IUT)
            </legend>
            <p>
              <label for="uri">
                <h4 style="margin-bottom: 0.5em">Location of CDB</h4>
              </label>
              <input id="uri" name="uri" size="128" type="text" value=""/>
            </p>
            <p>
              <label for="doc">
                <h4 style="margin-bottom: 0.5em">Upload CDB resource</h4>
              </label>
              <input name="doc" size="128" type="file" />
            </p>
            <p>
              <label for="level">Conformance Class:</label>
              <input checked="checked" id="level-1" name="level" type="radio" value="1"/>
              <label class="form-label" for="level-1">Level 1 | </label>
              <input id="level-2" name="level" type="radio" value="1,2"/>
              <label class="form-label" for="level-2">Level 1 &amp; Level 2</label>
            </p>
          </fieldset>
          <p>
            <input class="form-button" type="submit" value="Start"/>
          </p>
        </ctl:form>
      </xsl:variable>
      <xsl:variable name="iut-file" select="$form-data//value[@key='doc']/ctl:file-entry/@full-path"/>
      <xsl:variable name="test-run-props">
        <properties version="1.0">
          <entry key="iut">
            <xsl:choose>
              <xsl:when test="empty($iut-file)">
                <xsl:value-of select="$form-data//value[@key='uri']"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:copy-of select="concat('file:///', $iut-file)"/>
              </xsl:otherwise>
            </xsl:choose>
          </entry>
          <entry key="ics">
            <xsl:value-of select="$form-data/values/value[@key='level']"/>
          </entry>
        </properties>
      </xsl:variable>
      <xsl:variable name="testRunDir">
        <xsl:value-of select="tec:getTestRunDirectory($te:core)"/>
      </xsl:variable>
      <xsl:variable name="test-results">
        <ctl:call-function name="tns:run-ets-cdb10">
          <ctl:with-param name="testRunArgs" select="$test-run-props"/>
          <ctl:with-param name="outputDir" select="$testRunDir"/>
        </ctl:call-function>
      </xsl:variable>
      <xsl:call-template name="tns:testng-report">
        <xsl:with-param name="results" select="$test-results"/>
        <xsl:with-param name="outputDir" select="$testRunDir"/>
      </xsl:call-template>
      <xsl:variable name="summary-xsl" select="tec:findXMLResource($te:core, '/testng-summary.xsl')"/>
      <ctl:message>
        <xsl:value-of select="saxon:transform(saxon:compile-stylesheet($summary-xsl), $test-results)"/>See detailed test report in the TE_BASE/users/
        <xsl:value-of select="concat(substring-after($testRunDir, 'users/'), '/html/')"/>
        directory.</ctl:message>
      <xsl:if test="xs:integer($test-results/testng-results/@failed) gt 0">
        <xsl:for-each select="$test-results//test-method[@status='FAIL' and not(@is-config='true')]">
          <ctl:message>Test method 
            <xsl:value-of select="./@name"/>
            : 
            <xsl:value-of select=".//message"/></ctl:message>
        </xsl:for-each>
        <ctl:fail/>
      </xsl:if>
    </ctl:code>
  </ctl:test>
  <xsl:template name="tns:testng-report">
    <xsl:param name="results"/>
    <xsl:param name="outputDir"/>
    <xsl:variable name="stylesheet" select="tec:findXMLResource($te:core, '/testng-report.xsl')"/>
    <xsl:variable name="reporter" select="saxon:compile-stylesheet($stylesheet)"/>
    <xsl:variable as="node()*" name="report-params">
      <xsl:element name="testNgXslt.outputDir">
        <xsl:value-of select="concat($outputDir, '/html')"/>
      </xsl:element>
    </xsl:variable>
    <xsl:copy-of select="saxon:transform($reporter, $results, $report-params)"/>
  </xsl:template>
</ctl:package>