<?xml version="1.0" encoding="UTF-8"?>
<ctl:package xmlns:ctl="http://www.occamlab.com/ctl"
             xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
             xmlns:tns="http://www.opengis.net/cite/cdb10"
             xmlns:saxon="http://saxon.sf.net/"
             xmlns:tec="java:com.occamlab.te.TECore"
             xmlns:tng="java:org.opengis.cite.cdb10.TestNGController">

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
    <ctl:description>Checks implementations of the OGC CDB API for conformance
      against the candidate standard (OGC 15-113).
    </ctl:description>
    <ctl:starting-test>tns:Main</ctl:starting-test>
  </ctl:suite>

  <ctl:test name="tns:Main">
    <ctl:assertion>The test subject satisfies all applicable constraints.</ctl:assertion>
    <ctl:code>
      <xsl:variable name="form-data">
        <ctl:form method="POST" width="800" height="600" xmlns="http://www.w3.org/1999/xhtml">
          <h2>Test suite: ets-cdb10</h2>
          <div style="background:#F0F8FF" bgcolor="#F0F8FF">
            <p>The CDB implementation under test (IUT) is checked against the
              <a href="http://www.opengeospatial.org/projects/groups/cdbswg">OGC CDB 1.0</a>
            </p>
            <p>Detailed information about the test suite is available <a href="index.html" target="otherwindow">here</a>.
            </p>

            <p>One conformance level is defined:</p>

          </div>
          <fieldset style="background:#ccffff">
            <legend style="font-family: sans-serif; color: #000099;
			                 background-color:#F0F8FF; border-style: solid; 
                       border-width: medium; padding:4px">Implementation under test
            </legend>
            <p>
              <label for="uri">
                <h4 style="margin-bottom: 0.5em">Location of CDB</h4>
              </label>
              <input id="uri" name="uri" size="128" type="text" value=""/>
            </p>
            <p>
              <label for="level">Conformance class:</label>
              <input id="level-1" type="radio" name="level" value="1"
                     onclick="getElementById('directories').style.display='block'"/>
              <label class="form-label" for="level-1">CDB Structure</label>

              <input id="level-2" type="radio" name="level" value="2"
                     onclick="getElementById('directories').style.display='none'"/>
              <label class="form-label" for="level-2">level2</label>
            </p>


            <div id="directories" style="display:none">
              <div>
                <p>Geographic Coverage</p>
                <table frame="box">
                  <tr>
                    <td style="padding:10px">Latitude</td>
                    <td style="padding:10px"></td>
                    <td style="padding:10px">Longitude</td>
                    <td style="padding:10px"></td>
                  </tr>
                  <tr>
                    <td style="padding:10px">Minimum</td>
                    <td style="padding:10px">
                      <input id="minLat" name="minlat" max="90" min="-90" type="number" step="0.0001"/>
                    </td>
                    <td style="padding:10px">Minimum</td>
                    <td style="padding:10px">
                      <input id="minLong" name="minlong" max="180" min="-180" type="number" step="0.0001"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="padding:10px">Maximum</td>
                    <td style="padding:10px">
                      <input id="maxLat" name="maxlat" max="90" min="-90" type="number" step="0.0001"/>
                    </td>
                    <td style="padding:10px">Maximum</td>
                    <td style="padding:10px">
                      <input id="maxLong" name="maxlong" max="180" min="-180" type="number" step="0.0001"/>
                    </td>
                  </tr>
                </table>
              </div>

              <p>Datasets</p>

              <div style="float:left">
                <p>Tile</p>
                <table style="border:1px solid black">
                  <tr>
                    <td style="border:1px solid black"></td>
                    <td style="border:1px solid black">Tile Dataset</td>
                    <td style="border:1px solid black">Minimum LOD</td>
                    <td style="border:1px solid black">Maximum LOD</td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="elevationCBox" name="elevation" type="checkbox" value="001_Elevation"/>
                    </td>
                    <td style="border:1px solid black">001_Elevation</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="elevationMinLod" max="23" min="-10" name="elevationminlod"
                             type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="elevationMaxLod" max="23" min="-10" name="elevationmaxlod"
                             type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="minMaxElevationCBox" name="minmaxelevation" type="checkbox"
                             value="002_MinMaxElevation"/>
                    </td>
                    <td style="border:1px solid black">002_MinMaxElevation</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="minMaxElevationMinLod" max="23" min="-10"
                             name="minmaxelevationminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="minMaxElevationMaxLod" max="23" min="-10"
                             name="minmaxelevationmaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="maxCultureCBox" name="maxculture" type="checkbox" value="003_MaxCulture"/>
                    </td>
                    <td style="border:1px solid black">003_MaxCulture</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="maxCultureMinLod" max="23" min="-10" name="maxcultureminlod"
                             type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="maxCultureMaxLod" max="23" min="-10" name="maxculturemaxlod"
                             type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="imageryCBox" name="imagery" type="checkbox" value="004_Imagery"/>
                    </td>
                    <td style="border:1px solid black">004_Imagery</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="imageryMinLod" max="23" min="-10" name="imageryminlod"
                             type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="imageryMaxLod" max="23" min="-10" name="imagerymaxlod"
                             type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="rMTextureCBox" name="rmtexture" type="checkbox" value="005_RMTexture"/>
                    </td>
                    <td style="border:1px solid black">005_RMTexture</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="rMTextureMinLod" max="23" min="-10" name="rmtextureminlod"
                             type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="rMTextureMaxLod" max="23" min="-10" name="rmtexturemaxlod"
                             type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="rMDescriptorCBox" name="rmdescriptor" type="checkbox" value="006_RMDescriptor"/>
                    </td>
                    <td style="border:1px solid black">006_RMDescriptor</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="rMDescriptorMinLod" max="23" min="-10" name="rmdescriptorminlod"
                             type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="rMDescriptorMaxLod" max="23" min="-10" name="rmdescriptormaxlod"
                             type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gSFeatureCBox" name="gsfeature" type="checkbox" value="100_GSFeature"/>
                    </td>
                    <td style="border:1px solid black">100_GSFeature</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSFeatureMinLod" max="23" min="-10" name="gsfeatureminlod"
                             type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSFeatureMaxLod" max="23" min="-10" name="gsfeaturemaxlod"
                             type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gTFeatureCBox" name="gtfeature" type="checkbox" value="101_GTFeature"/>
                    </td>
                    <td style="border:1px solid black">101_GTFeature</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTFeatureMinLod" max="23" min="-10" name="gtfeatureminlod"
                             type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTFeatureMaxLod" max="23" min="-10" name="gtfeaturemaxlod"
                             type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="geoPoliticalCBox" name="geopolitical" type="checkbox" value="102_GeoPolitical"/>
                    </td>
                    <td style="border:1px solid black">102_GeoPolitical</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="geoPoliticalMinLod" max="23" min="-10" name="geopoliticalminlod"
                             type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="geoPoliticalMaxLod" max="23" min="-10" name="geopoliticalmaxlod"
                             type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="vectorMaterialCBox" name="vectormaterial" type="checkbox" value="200_VectorMaterial"/>
                    </td>
                    <td style="border:1px solid black">200_VectorMaterial</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="vectorMaterialMinLod" max="23" min="-10"
                             name="vectormaterialminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="vectorMaterialMaxLod" max="23" min="-10"
                             name="vectormaterialmaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="roadNetworkCBox" name="roadnetwork" type="checkbox" value="201_RoadNetwork"/>
                    </td>
                    <td style="border:1px solid black">201_RoadNetwork</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="roadNetworkMinLod" max="23" min="-10" name="roadnetworkminlod"
                             type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="roadNetworkMaxLod" max="23" min="-10" name="roadnetworkmaxlod"
                             type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="railRoadNetworkCBox" name="railroadnetwork" type="checkbox"
                             value="202_RailRoadNetwork"/>
                    </td>
                    <td style="border:1px solid black">202_RailRoadNetwork</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="railRoadNetworkMinLod" max="23" min="-10"
                             name="railroadnetworkminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="railRoadNetworkMaxLod" max="23" min="-10"
                             name="railroadnetworkmaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="powerLineNetworkCBox" name="powerlinenetwork" type="checkbox"
                             value="203_PowerLineNetwork"/>
                    </td>
                    <td style="border:1px solid black">203_PowerLineNetwork</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="powerLineNetworkMinLod" max="23" min="-10"
                             name="powerlinenetworkminlod" type="number" value="203_PowerLineNetwork_minLOD"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="powerLineNetworkMaxLod" max="23" min="-10"
                             name="powerlinenetworkmaxlod" type="number" value="203_PowerLineNetwork_maxLOD"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="hydrographyNetworkCBox" name="hydrographynetwork" type="checkbox"
                             value="204_HydrographyNetwork"/>
                    </td>
                    <td style="border:1px solid black">204_HydrographyNetwork</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="hydrographyNetworkMinLod" max="23" min="-10"
                             name="hydrographynetworkminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="hydrographyNetworkMaxLod" max="23" min="-10"
                             name="hydrographynetworkmaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gSModelGeometryCBox" name="gsmodelgeometry" type="checkbox"
                             value="300_GSModelGeometry"/>
                    </td>
                    <td style="border:1px solid black">300_GSModelGeometry</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelGeometryMinLod" max="23" min="-10"
                             name="gsmodelgeometryminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelGeometryMaxLod" max="23" min="-10"
                             name="gsmodelgeometrymaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gSModelTextureCBox" name="gsmodeltexture" type="checkbox" value="301_GSModelTexture"/>
                    </td>
                    <td style="border:1px solid black">301_GSModelTexture</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelTextureMinLod" max="23" min="-10"
                             name="gsmodeltextureminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelTextureMaxLod" max="23" min="-10"
                             name="gsmodeltexturemaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gSModelSignatureCBox" name="gsmodelsignature" type="checkbox"
                             value="302_GSModelSignature"/>
                    </td>
                    <td style="border:1px solid black">302_GSModelSignature</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelSignatureMinLod" max="23" min="-10"
                             name="gsmodelsignatureminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelSignatureMaxLod" max="23" min="-10"
                             name="gsmodelsignaturemaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gSModelDescriptorCBox" name="gsmodeldescriptor" type="checkbox"
                             value="303_GSModelDescriptor"/>
                    </td>
                    <td style="border:1px solid black">303_GSModelDescriptor</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelDescriptorMinLod" max="23" min="-10"
                             name="gsmodeldescriptorminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelDescriptorMaxLod" max="23" min="-10"
                             name="gsmodeldescriptormaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gSModelMaterialCBox" name="gsmodelmaterial" type="checkbox"
                             value="304_GSModelMaterial"/>
                    </td>
                    <td style="border:1px solid black">304_GSModelMaterial</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelMaterialMinLod" max="23" min="-10"
                             name="gsmodelmaterialminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelMaterialMaxLod" max="23" min="-10"
                             name="gsmodelmaterialmaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gSModelInteriorGeometryCBox" name="gsmodelinteriorgeometry" type="checkbox"
                             value="305_GSModelInteriorGeometry"/>
                    </td>
                    <td style="border:1px solid black">305_GSModelInteriorGeometry</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelInteriorGeometryMinLod" max="23" min="-10"
                             name="gsmodelinteriorgeometryminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelInteriorGeometryMaxLod" max="23" min="-10"
                             name="gsmodelinteriorgeometrymaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gSModelInteriorTextureCBox" name="gsmodelinteriortexture" type="checkbox"
                             value="306_GSModelInteriorTexture"/>
                    </td>
                    <td style="border:1px solid black">306_GSModelInteriorTexture</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelInteriorTextureMinLod" max="23" min="-10"
                             name="gsmodelinteriortextureminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelInteriorTextureMaxLod" max="23" min="-10"
                             name="gsmodelinteriortexturemaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gSModelInteriorDescriptorCBox" name="gsmodelinteriordescriptor" type="checkbox"
                             value="307_GSModelInteriorDescriptor"/>
                    </td>
                    <td style="border:1px solid black">307_GSModelInteriorDescriptor</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelInteriorDescriptorMinLod" max="23" min="-10"
                             name="gsmodelinteriordescriptorminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelInteriorDescriptorMaxLod" max="23" min="-10"
                             name="gsmodelinteriordescriptormaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gSModelInteriorMaterialCBox" name="gsmodelinteriormaterial" type="checkbox"
                             value="308_GSModelInteriorMaterial"/>
                    </td>
                    <td style="border:1px solid black">308_GSModelInteriorMaterial</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelInteriorMaterialMinLod" max="23" min="-10"
                             name="gsmodelinteriormaterialminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelInteriorMaterialMaxLod" max="23" min="-10"
                             name="gsmodelinteriormaterialmaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gSModelCMTCBox" name="gsmodelcmt" type="checkbox" value="309_GSModelCMT"/>
                    </td>
                    <td style="border:1px solid black">309_GSModelCMT</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelCMTMinLod" max="23" min="-10" name="gsmodelcmtminlod"
                             type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gSModelCMTMaxLod" max="23" min="-10" name="gsmodelcmtmaxlod"
                             type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="t2dModelGeometryCBox" name="t2dmodelgeometry" type="checkbox"
                             value="310_T2DModelGeometry"/>
                    </td>
                    <td style="border:1px solid black">310_T2DModelGeometry</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="t2dModelGeometryMinLod" max="23" min="-10"
                             name="t2dmodelgeometryminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="t2dModelGeometryMaxLod" max="23" min="-10"
                             name="t2dmodelgeometrymaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="navigationCBox" name="navigation" type="checkbox" value="401_Navigation"/>
                    </td>
                    <td style="border:1px solid black">401_Navigation</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="navigationMinLod" max="23" min="-10" name="navigationminlod"
                             type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="navigationMaxLod" max="23" min="-10" name="navigationmaxlod"
                             type="number"/>
                    </td>
                  </tr>
                </table>
                <p>Navigation</p>

                <table style="border:1px solid black">
                  <tr>
                    <td style="border:1px solid black"></td>
                    <td style="border:1px solid black">Navigation Dataset</td>
                    <td style="border:1px solid black">Minimum LOD</td>
                    <td style="border:1px solid black">Maximum LOD</td>
                  </tr>

                  <tr>
                    <td style="border:1px solid black">
                      <input id="navDataCBox" name="navdata" type="checkbox" value="400_NavData"/>
                    </td>
                    <td style="border:1px solid black">400_NavData</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="navDataMinLod" max="23" min="-10" name="navdataminlod"
                             type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="navDataMaxLod" max="23" min="-10" name="navdatamaxlod"
                             type="number"/>
                    </td>
                  </tr>

                </table>
              </div>

              <div style="float:left;margin-left:20px">

                <p>GeoTypical Model</p>
                <table style="border:1px solid black">
                  <tr>
                    <td style="border:1px solid black"></td>
                    <td style="border:1px solid black">GeoTypical Dataset</td>
                    <td style="border:1px solid black">Minimum LOD</td>
                    <td style="border:1px solid black">Maximum LOD</td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gTModelGeometryCBox" name="gtmodelgeometry" type="checkbox"
                             value="500_GTModelGeometry"/>
                    </td>
                    <td style="border:1px solid black">500_GTModelGeometry</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelGeometryMinLod" max="23" min="-10"
                             name="gtmodelgeometryminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelGeometryMaxLod" max="23" min="-10"
                             name="gtmodelgeometrymaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gTModelTextureCBox" name="gtmodeltexture" type="checkbox" value="501_GTModelTexture"/>
                    </td>
                    <td style="border:1px solid black">501_GTModelTexture</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelTextureMinLod" max="23" min="-10"
                             name="gtmodeltextureminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelTextureMaxLod" max="23" min="-10"
                             name="gtmodeltexturemaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gTModelSignatureCBox" name="gtmodelsignature" type="checkbox"
                             value="502_GTModelSignature"/>
                    </td>
                    <td style="border:1px solid black">502_GTModelSignature</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelSignatureMinLod" max="23" min="-10"
                             name="gtmodelsignatureminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelSignatureMaxLod" max="23" min="-10"
                             name="gtmodelsignaturemaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gTModelDescriptorCBox" name="gtmodeldescriptor" type="checkbox"
                             value="503_GTModelDescriptor"/>
                    </td>
                    <td style="border:1px solid black">503_GTModelDescriptor</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelDescriptorMinLod" max="23" min="-10"
                             name="gtmodeldescriptorminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelDescriptorMaxLod" max="23" min="-10"
                             name="gtmodeldescriptormaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gTModelMaterialCBox" name="gtmodelmaterial" type="checkbox"
                             value="504_GSModelMaterial"/>
                    </td>
                    <td style="border:1px solid black">504_GTModelMaterial</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelMaterialMinLod" max="23" min="-10"
                             name="gtmodelmaterialminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelMaterialMaxLod" max="23" min="-10"
                             name="gtmodelmaterialmaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gTModelCMTCBox" name="gtmodelcmt" type="checkbox" value="505_GSModelCMT"/>
                    </td>
                    <td style="border:1px solid black">505_GTModelCMT</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelCMTMinLod" max="23" min="-10" name="gtmodelcmtminlod"
                             type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelCMTMaxLod" max="23" min="-10" name="gtmodelcmtmaxlod"
                             type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gTModelInteriorGeometryCBox" name="gtmodelinteriorgeometry" type="checkbox"
                             value="506_GTModelInteriorGeometry"/>
                    </td>
                    <td style="border:1px solid black">506_GTModelInteriorGeometry</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelInteriorGeometryMinLod" max="23" min="-10"
                             name="gtmodelinteriorgeometryminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelInteriorGeometryMaxLod" max="23" min="-10"
                             name="gtmodelinteriorgeometrymaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gTModelInteriorTextureCBox" name="gtmodelinteriortexture" type="checkbox"
                             value="507_GTModelInteriorTexture"/>
                    </td>
                    <td style="border:1px solid black">507_GTModelInteriorTexture</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelInteriorTextureMinLod" max="23" min="-10"
                             name="gtmodelinteriortextureminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelInteriorTextureMaxLod" max="23" min="-10"
                             name="gtmodelinteriortexturemaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gTModelInteriorDescriptorCBox" name="gtmodelinteriordescriptor" type="checkbox"
                             value="508_GTModelInteriorDescriptor"/>
                    </td>
                    <td style="border:1px solid black">508_GTModelInteriorDescriptor</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelInteriorDescriptorMinLod" max="23" min="-10"
                             name="gtmodelinteriordescriptorminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelInteriorDescriptorMaxLod" max="23" min="-10"
                             name="gtmodelinteriordescriptormaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gTModelInteriorMaterialCBox" name="gtmodelinteriormaterial" type="checkbox"
                             value="509_GTModelInteriorMaterial"/>
                    </td>
                    <td style="border:1px solid black">509_GTModelInteriorMaterial</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelInteriorMaterialMinLod" max="23" min="-10"
                             name="gtmodelinteriormaterialminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelInteriorMaterialMaxLod" max="23" min="-10"
                             name="gtmodelinteriormaterialmaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gTModelTextureCBox2" name="gtmodeltexture2" type="checkbox"
                             value="511_GTModelTexture"/>
                    </td>
                    <td style="border:1px solid black">511_GTModelTexture</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelTextureMinLod2" max="23" min="-10"
                             name="gtmodeltextureminlod2" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelTextureMaxLod2" max="23" min="-10"
                             name="gtmodeltexturemaxlod2" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gTModelSignatureCBox2" name="gtmodelsignature2" type="checkbox"
                             value="512_GTModelSignature"/>
                    </td>
                    <td style="border:1px solid black">512_GTModelSignature</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelSignatureMinLod2" max="23" min="-10"
                             name="gtmodelsignatureminlod2" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelSignatureMaxLod2" max="23" min="-10"
                             name="gtmodelsignaturemaxlod2" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="gTModelInteriorCMTCBox" name="gtmodelinteriorcmt" type="checkbox"
                             value="513_GTModelInteriorCMT"/>
                    </td>
                    <td style="border:1px solid black">513_GTModelInteriorCMT</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelInteriorCMTMinLod" max="23" min="-10"
                             name="gtmodelinteriorcmtminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="gTModelInteriorCMTMaxLod" max="23" min="-10"
                             name="gtmodelinteriorcmtmaxlod" type="number"/>
                    </td>
                  </tr>
                </table>
                <p>Moving Model</p>
                <table style="border:1px solid black">
                  <tr>
                    <td style="border:1px solid black"></td>
                    <td style="border:1px solid black">Moving Model Dataset</td>
                    <td style="border:1px solid black">Minimum LOD</td>
                    <td style="border:1px solid black">Maximum LOD</td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="mModelGeometryCBox" name="mmodelgeometry" type="checkbox" value="600_MModelGeometry"/>
                    </td>
                    <td style="border:1px solid black">600_MModelGeometry</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="mModelGeometryMinLod" max="23" min="-10"
                             name="mmodelgeometryminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="mModelGeometryMaxLod" max="23" min="-10"
                             name="mmodelgeometrymaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="mModelTextureCBox" name="mmodeltexture" type="checkbox" value="601_MModelTexture"/>
                    </td>
                    <td style="border:1px solid black">601_MModelTexture</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="mModelTextureMinLod" max="23" min="-10" name="mmodeltextureminlod"
                             type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="mModelTextureMaxLod" max="23" min="-10" name="mmodeltexturemaxlod"
                             type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="mModelSignatureCBox" name="mmodelsignature" type="checkbox"
                             value="602_MModelSignature"/>
                    </td>
                    <td style="border:1px solid black">602_MModelSignature</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="mModelSignatureMinLod" max="23" min="-10"
                             name="mmodelsignatureminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="mModelSignatureMaxLod" max="23" min="-10"
                             name="mmodelsignaturemaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="mModelDescriptorCBox" name="mmodeldescriptor" type="checkbox"
                             value="603_MModelDescriptor"/>
                    </td>
                    <td style="border:1px solid black">603_MModelDescriptor</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="mModelDescriptorMinLod" max="23" min="-10"
                             name="mmodeldescriptorminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="mModelDescriptorMaxLod" max="23" min="-10"
                             name="mmodeldescriptormaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="mModelMaterialCBox" name="mmodelmaterial" type="checkbox" value="604_GSModelMaterial"/>
                    </td>
                    <td style="border:1px solid black">604_MModelMaterial</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="mModelMaterialMinLod" max="23" min="-10"
                             name="mmodelmaterialminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="mModelMaterialMaxLod" max="23" min="-10"
                             name="mmodelmaterialmaxlod" type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="mModelCMTCBox" name="mmodelcmt" type="checkbox" value="605_GSModelCMT"/>
                    </td>
                    <td style="border:1px solid black">605_MModelCMT</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="mModelCMTMinLod" max="23" min="-10" name="mmodelcmtminlod"
                             type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="mModelCMTMaxLod" max="23" min="-10" name="mmodelcmtmaxlod"
                             type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="mModelSignatureCBox2" name="mmodelsignature2" type="checkbox"
                             value="606_MModelSignature"/>
                    </td>
                    <td style="border:1px solid black">606_MModelSignature</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="mModelSignatureMinLod2" max="23" min="-10"
                             name="mmodelsignatureminlod2" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="mModelSignatureMaxLod2" max="23" min="-10"
                             name="mmodelsignaturemaxlod2" type="number"/>
                    </td>
                  </tr>
                </table>
                <p>Metadata</p>

                <table style="border:1px solid black">
                  <tr>
                    <td style="border:1px solid black"></td>
                    <td style="border:1px solid black">Metadata Dataset</td>
                    <td style="border:1px solid black">Minimum LOD</td>
                    <td style="border:1px solid black">Maximum LOD</td>
                  </tr>

                  <tr>
                    <td style="border:1px solid black">
                      <input id="metadataCBox" name="metadata" type="checkbox" value="700_Metadata"/>
                    </td>
                    <td style="border:1px solid black">700_Metadata</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="metadataMinLod" max="23" min="-10" name="metadataminlod"
                             type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="metadataMaxLod" max="23" min="-10" name="metadatamaxlod"
                             type="number"/>
                    </td>
                  </tr>
                  <tr>
                    <td style="border:1px solid black">
                      <input id="clientSpecificCBox" name="clientspecific" type="checkbox" value="701_ClientSpecific"/>
                    </td>
                    <td style="border:1px solid black">701_ClientSpecific</td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="clientSpecificMinLod" max="23" min="-10"
                             name="clientspecificminlod" type="number"/>
                    </td>
                    <td style="border:1px solid black">
                      <input style="width:120px" id="clientSpecificMaxLod" max="23" min="-10"
                             name="clientspecificmaxlod" type="number"/>
                    </td>
                  </tr>
                </table>
              </div>

            </div>
          </fieldset>
          <p>
            <input class="form-button" type="submit" value="Start"/>
            |
            <input class="form-button" type="reset" value="Clear"/>
          </p>
        </ctl:form>
      </xsl:variable>
      <xsl:variable name="iut-file" select="$form-data//value[@key='doc']/ctl:file-entry/@full-path"/>
      <xsl:variable name="test-run-props">
        <properties version="1.0">
          <entry key="iut">
            <xsl:choose>
              <xsl:when test="empty($iut-file)">
                <xsl:value-of select="normalize-space($form-data/values/value[@key='uri'])"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:copy-of select="concat('file:///', $iut-file)"/>
              </xsl:otherwise>
            </xsl:choose>
          </entry>
          <entry key="ics">
            <xsl:value-of select="$form-data/values/value[@key='level']"/>
          </entry>
          <entry key="directories">
            <xsl:value-of
                select="concat($form-data/values/value[@key='elevation'],'-',$form-data/values/value[@key='minmaxelevation'],'-',$form-data/values/value[@key='maxculture'],'-',$form-data/values/value[@key='imagery'],'-',$form-data/values/value[@key='rmtexture'],'-',$form-data/values/value[@key='rmdescriptor'],'-',$form-data/values/value[@key='gsfeature'],'-',$form-data/values/value[@key='gtfeature'],'-',$form-data/values/value[@key='geopolitical'],'-',$form-data/values/value[@key='vectormaterial'],'-',$form-data/values/value[@key='roadnetwork'],'-',$form-data/values/value[@key='railroadnetwork'],'-',$form-data/values/value[@key='powerlinenetwork'],'-',$form-data/values/value[@key='hydrographynetwork'],'-',$form-data/values/value[@key='gsmodelgeometry'],'-',$form-data/values/value[@key='gsmodeltexture'],'-',$form-data/values/value[@key='gsmodelsignature'],'-',$form-data/values/value[@key='gsmodeldescriptor'],'-',$form-data/values/value[@key='gsmodelmaterial'],'-',$form-data/values/value[@key='gsmodelinteriorgeometry'],'-',$form-data/values/value[@key='gsmodelinteriortexture'],'-',$form-data/values/value[@key='gsmodelinteriordescriptor'],'-',$form-data/values/value[@key='gsmodelinteriormaterial'],'-',$form-data/values/value[@key='gsmodelcmt'],'-',$form-data/values/value[@key='t2dmodelgeometry'],'-',$form-data/values/value[@key='navigation'],'-',$form-data/values/value[@key='navdata'],'-',$form-data/values/value[@key='gtmodelgeometry'],'-',$form-data/values/value[@key='gtmodeltexture'],'-',$form-data/values/value[@key='gtmodelsignature'],'-',$form-data/values/value[@key='gtmodeldescriptor'],'-',$form-data/values/value[@key='gtmodelmaterial'],'-',$form-data/values/value[@key='gtmodelcmt'],'-',$form-data/values/value[@key='gtmodelinteriorgeometry'],'-',$form-data/values/value[@key='gtmodelinteriortexture'],'-',$form-data/values/value[@key='gtmodelinteriordescriptor'],'-',$form-data/values/value[@key='gtmodelinteriormaterial'],'-',$form-data/values/value[@key='gtmodeltexture2'],'-',$form-data/values/value[@key='gtmodelsignature2'],'-',$form-data/values/value[@key='gtmodelinteriorcmt'],'-',$form-data/values/value[@key='mmodelgeometry'],'-',$form-data/values/value[@key='mmodeltexture'],'-',$form-data/values/value[@key='mmodelsignature'],'-',$form-data/values/value[@key='mmodeldescriptor'],'-',$form-data/values/value[@key='mmodelmaterial'],'-',$form-data/values/value[@key='mmodelcmt'],'-',$form-data/values/value[@key='mmodelsignature2'],'-',$form-data/values/value[@key='metadata'],'-',$form-data/values/value[@key='clientspecific'])"/>
          </entry>
          <entry key="latlong">
            <xsl:value-of
                select="concat($form-data/values/value[@key='minlat'],'_',$form-data/values/value[@key='minlong'],'_',$form-data/values/value[@key='maxlat'],'_',$form-data/values/value[@key='maxlong'])"/>
          </entry>
          <entry key="minmaxlod">
            <xsl:value-of
                select="concat('001_Elevation@min','#',$form-data/values/value[@key='elevationminlod'],'#','001_Elevation@max','#',$form-data/values/value[@key='elevationmaxlod'],'#','002_MinMaxElevation@min','#',$form-data/values/value[@key='minmaxelevationminlod'],'#','002_MinMaxElevation@max','#',$form-data/values/value[@key='minmaxelevationmaxlod'],'#','003_MaxCulture@min','#',$form-data/values/value[@key='maxcultureminlod'],'#','003_MaxCulture@max','#',$form-data/values/value[@key='maxculturemaxlod'],'#','004_Imagery@min','#',$form-data/values/value[@key='imageryminlod'],'#','004_Imagery@max','#',$form-data/values/value[@key='imagerymaxlod'],'#','005_RMTexture@min','#',$form-data/values/value[@key='rmtextureminlod'],'#','005_RMTexture@max','#',$form-data/values/value[@key='rmtexturemaxlod'],'#','006_RMDescriptor@min','#',$form-data/values/value[@key='rmdescriptorminlod'],'#','006_RMDescriptor@max','#',$form-data/values/value[@key='rmdescriptormaxlod'],'#','100_GSFeature@min','#',$form-data/values/value[@key='gsfeatureminlod'],'#','100_GSFeature@max','#',$form-data/values/value[@key='gsfeaturemaxlod'],'#','101_GTFeature@min','#',$form-data/values/value[@key='gtfeatureminlod'],'#','101_GTFeature@max','#',$form-data/values/value[@key='gtfeaturemaxlod'],'#','102_GeoPolitical@min','#',$form-data/values/value[@key='geopoliticalminlod'],'#','102_GeoPolitical@max','#',$form-data/values/value[@key='geopoliticalmaxlod'],'#','200_VectorMaterial@min','#',$form-data/values/value[@key='vectormaterialminlod'],'#','200_VectorMaterial@max','#',$form-data/values/value[@key='vectormaterialmaxlod'],'#','201_RoadNetwork@min','#',$form-data/values/value[@key='roadnetworkminlod'],'#','201_RoadNetwork@max','#',$form-data/values/value[@key='roadnetworkmaxlod'],'#','202_RailRoadNetwork@min','#',$form-data/values/value[@key='railroadnetworkminlod'],'#','202_RailRoadNetwork@max','#',$form-data/values/value[@key='railroadnetworkmaxlod'],'#','203_PowerLineNetwork@min','#',$form-data/values/value[@key='powerlinenetworkminlod'],'#','203_PowerLineNetwork@max','#',$form-data/values/value[@key='powerlinenetworkmaxlod'],'#','204_HydrographyNetwork@min','#',$form-data/values/value[@key='hydrographynetworkminlod'],'#','204_HydrographyNetwork@max','#',$form-data/values/value[@key='hydrographynetworkmaxlod'],'#','300_GSModelGeometry@min','#',$form-data/values/value[@key='gsmodelgeometryminlod'],'#','300_GSModelGeometry@max','#',$form-data/values/value[@key='gsmodelgeometrymaxlod'],'#','301_GSModelTexture@min','#',$form-data/values/value[@key='gsmodeltextureminlod'],'#','301_GSModelTexture@max','#',$form-data/values/value[@key='gsmodeltexturemaxlod'],'#','302_GSModelSignature@min','#',$form-data/values/value[@key='gsmodelsignatureminlod'],'#','302_GSModelSignature@max','#',$form-data/values/value[@key='gsmodelsignaturemaxlod'],'#','303_GSModelDescriptor@min','#',$form-data/values/value[@key='gsmodeldescriptorminlod'],'#','303_GSModelDescriptor@max','#',$form-data/values/value[@key='gsmodeldescriptormaxlod'],'#','304_GSModelMaterial@min','#',$form-data/values/value[@key='gsmodelmaterialminlod'],'#','304_GSModelMaterial@max','#',$form-data/values/value[@key='gsmodelmaterialmaxlod'],'#','305_GSModelInteriorGeometry@min','#',$form-data/values/value[@key='gsmodelinteriorgeometryminlod'],'#','305_GSModelInteriorGeometry@max','#',$form-data/values/value[@key='gsmodelinteriorgeometrymaxlod'],'#','306_GSModelInteriorTexture@min','#',$form-data/values/value[@key='gsmodelinteriortextureminlod'],'#','306_GSModelInteriorTexture@max','#',$form-data/values/value[@key='gsmodelinteriortexturemaxlod'],'#','307_GSModelInteriorDescriptor@min','#',$form-data/values/value[@key='gsmodelinteriordescriptorminlod'],'#','307_GSModelInteriorDescriptor@max','#',$form-data/values/value[@key='gsmodelinteriordescriptormaxlod'],'#','308_GSModelInteriorMaterial@min','#',$form-data/values/value[@key='gsmodelinteriormaterialminlod'],'#','308_GSModelInteriorMaterial@max','#',$form-data/values/value[@key='gsmodelinteriormaterialmaxlod'],'#','309_GSModelCMT@min','#',$form-data/values/value[@key='gsmodelcmtminlod'],'#','309_GSModelCMT@max','#',$form-data/values/value[@key='gsmodelcmtmaxlod'],'#','310_T2DModelGeometry@min','#',$form-data/values/value[@key='t2dmodelgeometryminlod'],'#','310_T2DModelGeometry@max','#',$form-data/values/value[@key='t2dmodelgeometrymaxlod'],'#','401_Navigation@min','#',$form-data/values/value[@key='navigationminlod'],'#','401_Navigation@max','#',$form-data/values/value[@key='navigationmaxlod'],'#','400_NavData@min','#',$form-data/values/value[@key='navdataminlod'],'#','400_NavData@max','#',$form-data/values/value[@key='navdatamaxlod'],'#','500_GTModelGeometry@min','#',$form-data/values/value[@key='gtmodelgeometryminlod'],'#','500_GTModelGeometry@max','#',$form-data/values/value[@key='gtmodelgeometrymaxlod'],'#','501_GTModelTexture@min','#',$form-data/values/value[@key='gtmodeltextureminlod'],'#','501_GTModelTexture@max','#',$form-data/values/value[@key='gtmodeltexturemaxlod'],'#','502_GTModelSignature@min','#',$form-data/values/value[@key='gtmodelsignatureminlod'],'#','502_GTModelSignature@max','#',$form-data/values/value[@key='gtmodelsignaturemaxlod'],'#','503_GTModelDescriptor@min','#',$form-data/values/value[@key='gtmodeldescriptorminlod'],'#','503_GTModelDescriptor@max','#',$form-data/values/value[@key='gtmodeldescriptormaxlod'],'#','504_GSModelMaterial@min','#',$form-data/values/value[@key='gtmodelmaterialminlod'],'#','504_GSModelMaterial@max','#',$form-data/values/value[@key='gtmodelmaterialmaxlod'],'#','505_GSModelCMT@min','#',$form-data/values/value[@key='gtmodelcmtminlod'],'#','505_GSModelCMT@max','#',$form-data/values/value[@key='gtmodelcmtmaxlod'],'#','506_GTModelInteriorGeometry@min','#',$form-data/values/value[@key='gtmodelinteriorgeometryminlod'],'#','506_GTModelInteriorGeometry@max','#',$form-data/values/value[@key='gtmodelinteriorgeometrymaxlod'],'#','507_GTModelInteriorTexture@min','#',$form-data/values/value[@key='gtmodelinteriortextureminlod'],'#','507_GTModelInteriorTexture@max','#',$form-data/values/value[@key='gtmodelinteriortexturemaxlod'],'#','508_GTModelInteriorDescriptor@min','#',$form-data/values/value[@key='gtmodelinteriordescriptorminlod'],'#','508_GTModelInteriorDescriptor@max','#',$form-data/values/value[@key='gtmodelinteriordescriptormaxlod'],'#','509_GTModelInteriorMaterial@min','#',$form-data/values/value[@key='gtmodelinteriormaterialminlod'],'#','509_GTModelInteriorMaterial@max','#',$form-data/values/value[@key='gtmodelinteriormaterialmaxlod'],'#','511_GTModelTexture@min','#',$form-data/values/value[@key='gtmodeltextureminlod2'],'#','511_GTModelTexture@max','#',$form-data/values/value[@key='gtmodeltexturemaxlod2'],'#','512_GTModelSignature@min','#',$form-data/values/value[@key='gtmodelsignatureminlod2'],'#','512_GTModelSignature@max','#',$form-data/values/value[@key='gtmodelsignaturemaxlod2'],'#','513_GTModelInteriorCMT@min','#',$form-data/values/value[@key='gtmodelinteriorcmtminlod'],'#','513_GTModelInteriorCMT@max','#',$form-data/values/value[@key='gtmodelinteriorcmtmaxlod'],'#','600_MModelGeometry@min','#',$form-data/values/value[@key='mmodelgeometryminlod'],'#','600_MModelGeometry@max','#',$form-data/values/value[@key='mmodelgeometrymaxlod'],'#','601_MModelTexture@min','#',$form-data/values/value[@key='mmodeltextureminlod'],'#','601_MModelTexture@max','#',$form-data/values/value[@key='mmodeltexturemaxlod'],'#','602_MModelSignature@min','#',$form-data/values/value[@key='mmodelsignatureminlod'],'#','602_MModelSignature@max','#',$form-data/values/value[@key='mmodelsignaturemaxlod'],'#','603_MModelDescriptor@min','#',$form-data/values/value[@key='mmodeldescriptorminlod'],'#','603_MModelDescriptor@max','#',$form-data/values/value[@key='mmodeldescriptormaxlod'],'#','604_GSModelMaterial@min','#',$form-data/values/value[@key='mmodelmaterialminlod'],'#','604_GSModelMaterial@max','#',$form-data/values/value[@key='mmodelmaterialmaxlod'],'#','605_GSModelCMT@min','#',$form-data/values/value[@key='mmodelcmtminlod'],'#','605_GSModelCMT@max','#',$form-data/values/value[@key='mmodelcmtmaxlod'],'#','606_MModelSignature@min','#',$form-data/values/value[@key='mmodelsignatureminlod2'],'#','606_MModelSignature@max','#',$form-data/values/value[@key='mmodelsignaturemaxlod2'],'#','700_Metadata@min','#',$form-data/values/value[@key='metadataminlod'],'#','700_Metadata@max','#',$form-data/values/value[@key='metadatamaxlod'],'#','701_ClientSpecific@min','#',$form-data/values/value[@key='clientspecificminlod'],'#','701_ClientSpecific@max','#',$form-data/values/value[@key='clientspecificmaxlod'])"/>
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
        <xsl:value-of select="saxon:transform(saxon:compile-stylesheet($summary-xsl), $test-results)"/>
        See detailed test report in the TE_BASE/users/
        <xsl:value-of
            select="concat(substring-after($testRunDir, 'users/'), '/html/')"/>
        directory.
      </ctl:message>
      <xsl:if test="xs:integer($test-results/testng-results/@failed) gt 0">
        <xsl:for-each select="$test-results//test-method[@status='FAIL' and not(@is-config='true')]">
          <ctl:message>
            Test method<xsl:value-of select="./@name"/>:
            <xsl:value-of select=".//message"/>
          </ctl:message>
        </xsl:for-each>
        <ctl:fail/>
      </xsl:if>
      <xsl:if
          test="xs:integer($test-results/testng-results/@skipped) eq xs:integer($test-results/testng-results/@total)">
        <ctl:message>All tests were skipped. One or more preconditions were not satisfied.</ctl:message>
        <xsl:for-each select="$test-results//test-method[@status='FAIL' and @is-config='true']">
          <ctl:message>
            <xsl:value-of select="./@name"/>:
            <xsl:value-of select=".//message"/>
          </ctl:message>
        </xsl:for-each>
        <ctl:skipped/>
      </xsl:if>
    </ctl:code>
  </ctl:test>

  <xsl:template name="tns:testng-report">
    <xsl:param name="results"/>
    <xsl:param name="outputDir"/>
    <xsl:variable name="stylesheet" select="tec:findXMLResource($te:core, '/testng-report.xsl')"/>
    <xsl:variable name="reporter" select="saxon:compile-stylesheet($stylesheet)"/>
    <xsl:variable name="report-params" as="node()*">
      <xsl:element name="testNgXslt.outputDir">
        <xsl:value-of select="concat($outputDir, '/html')"/>
      </xsl:element>
    </xsl:variable>
    <xsl:copy-of select="saxon:transform($reporter, $results, $report-params)"/>
  </xsl:template>
</ctl:package>
