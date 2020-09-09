
The following table lists the OGC CDB 1.0 requirements and if they are implemented in the OGC CDB Cite tests.


<table>
  <tr>
   <td><strong>Requirement Test Classes (OGC 15-113r5)</strong>
   </td>
   <td><strong>Implemented in the CDB CITE Test</strong>
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.bnszl3a4s4f">A.1.1 General CDB Database and Implementation</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.bea1chgczx9g">A.1.2 Platform</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.69gsy4bl5ssy">A.1.3 General Data Representation</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.6ts020hxg91q">A.1.4 Structure-Tiling Model</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.ntpi7vb38au">A.1.5 Light Naming</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.hwaqg3mg2yxq">A.1.6 Light Name Hierarchy</a></strong>
   </td>
   <td>Implemented:<a href="https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/metadataAndVersioning/LightsXxxXmlStructureTests.java"> https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/metadataAndVersioning/LightsXxxXmlStructureTests.java</a>
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.sgikdehkryq4">A.1.7 Materials</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.24n49tpabwfn">A.1.8 CDB Root Directory</a></strong>
   </td>
   <td>Implemented:<a href="https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/RootStructureTests.java"> https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/RootStructureTests.java</a>
   </td>
  </tr>
  <tr>
   <td>Most CDB datasets are organized in a tile structure and stored under \CDB\Tiles\ directory. The tile structure facilitates access to the information in real-time by the runtime client-devices. However, for some datasets such as Moving Models or geotypical models datasets that require minimal storage, there is no significant advantage to be added from such a tile structure. Such datasets are referred to as global datasets: they consist of data elements that are global to the earth, i.e., no structure other than the datasets is provided.
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.frwjt6gii52q">A.1.9 Version Metadata File</a></strong>
   </td>
   <td>Implemented:<a href="https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/metadataAndVersioning/VersionXmlStructureTests.java"> https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/metadataAndVersioning/VersionXmlStructureTests.java</a>
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.5n8eako78p8z">A.1.10 FLIR Metadata File</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.7naysplabvx3">A.1.11 Database Version Directory Structure</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.3xw5xpucouqh">A.1.12 Model Types</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.2sklbyojjkaz">A.1.13 Geospecific Model (GSModel) Storage</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.7vgeci59a68b">A.1.14 Geotypical Models (GTModel) Naming Conventions</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented: https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelStructureTests.java
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented: https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelGeometryStructureTests.java
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented: https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelGeometryStructureTests.java
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented:<a href="https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelDescriptorStructureTests.java"> https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelDescriptorStructureTests.java</a>
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented: https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelStructureTests.java
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented:<a href="https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelMaterialStructureTests.java"> https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelMaterialStructureTests.java</a>
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented:<a href="https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelCMTStructureTests.java"> https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelCMTStructureTests.java</a>
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented:<a href="https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelInteriorGeometryStructureTests.java"> https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelInteriorGeometryStructureTests.java</a>
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented:<a href="https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelInteriorDescriptorStructureTests.java"> https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelInteriorDescriptorStructureTests.java</a>
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented:<a href="https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelInteriorTextureStructureTests.java"> https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelInteriorTextureStructureTests.java</a>
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented:<a href="https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelInteriorMaterialStructureTests.java"> https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelInteriorMaterialStructureTests.java</a>
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented:<a href="https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelSignatureStructureTests.java"> https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/GTModelSignatureStructureTests.java</a>
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.3mlwd6ocuk8g">A.1.15 Moving Model (MModel) Naming Conventions</a></strong>
   </td>
   <td>Implemented:<a href="https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/MModelStructureTests.java"> https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/MModelStructureTests.java</a>
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented: https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/MModelGeometryStructureTests.java
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented:<a href="https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/MModelTextureStructureTests.java"> https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/MModelTextureStructureTests.java</a>
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented:<a href="https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/MModelSignatureStructureTests.java"> https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/MModelSignatureStructureTests.java</a>
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.d4vkxkek05zn">A.1.16 Tiled Datasets</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented: https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/TilesStructureTests.java
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented: https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/TilesStructureTests.java
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented: https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/TilesStructureTests.java
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.f1b4196638ni">A.1.17 Archive Names</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.jotnzz1lvvz0">A.1.18 NavData Naming Convention</a></strong>
   </td>
   <td>Implemented:<a href="https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/NavigationLibraryStructureTests.java"> https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/cdbStructure/NavigationLibraryStructureTests.java</a>
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.8qaphqpkwwnr">A.1.19 Metadata Datasets</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented: https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/metadataAndVersioning/CDBAttributesXmlStructureTests.java
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented: https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/metadataAndVersioning/CDBAttributesXmlStructureTests.java
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented: https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/metadataAndVersioning/CDBAttributesXmlStructureTests.java
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>Implemented: https://github.com/opengeospatial/ets-cdb10/blob/master/src/main/java/org/opengis/cite/cdb10/metadataAndVersioning/CDBAttributesXmlStructureTests.java
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.ivzc92dunlle">A.1.20 Navigation Data</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.2fkb6uct346c">A.1.21 Tiled Raster Datasets</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.f5vrds3hs03e">A.1.22 Tiled Elevation Dataset</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.177g486j7ofk">A.1.23 Tiled Terrain Bathymetry</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.l5l2hswsbeca">A.1.24 Tiled JPEG Metadata</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><span style="text-decoration:underline;">A.1.25 Visible Spectrum Terrain Imagery (VSTI)</span></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.ga0c7wimgdax">A.1.26 Visible Spectrum Terrain Light Map (VSTLM)</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.tu7g70gbsomy">A.1.27 Raster Composite Material</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.lipkjwxtu23g">A.1.28 Tiled Vector Datasets</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.9zzhyqx6dx0v">A.1.29 Vector Datasets Mandatory Attribute Usage</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.1k81oe6a0w2n">A.1.30 Vector Datasets Topology</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.4p28372rt28o">A.1.31 Elevation Constraints</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.n2e3zgdw1p2m">A.1.32 Tiled Road Networks</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.ilnh2savi320">A.1.33 Tiled Railroad Networks</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.gr49xcnej0nr">A.1.34 Tiled PowerLine Networks</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.oeuj6z6tz15w">A.1.35 Tiled Hydrography Networks</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
  <tr>
   <td><strong><a href="https://docs.google.com/document/d/1FxoNaObHyBcZ6r-IIkz1z_Zmwgl2nIJtL6ZuoMsdNw8/edit#heading=h.ifraj1cu4mj6">A.1.36 Vector Composite Material Table (VCMT)</a></strong>
   </td>
   <td>NA
   </td>
  </tr>
</table>