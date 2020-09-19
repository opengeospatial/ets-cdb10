package org.opengis.cite.cdb10.util;

/**
 * Class to hold filename matching patterns.
 * See OGC 15-113r5, Section 3.6.3.2.5 for more examples.
 *
 */
public class FilenamePatterns {

	private FilenamePatterns() {
	}
	
	/**
	 * General format for GSModel archive file names
	 */
	public static final String GSModelArchive = "^(?<lat>(S|N)[0-9]{2})(?<lon>(E|W)[0-9]{3})_D(?<datasetCode>[0-9]{3})_S(?<cs1>[0-9]{3})_T(?<cs2>[0-9]{3})_(?<lod>LC\\d{2}|L[0-9]{2})_(?<uref>U[0-9]+)_(?<rref>R[0-9]+)\\.(?<ext>.+)$";
	
	/**
	 * Example of valid filename: N62W162_D303_S001_T001_L07_U38_R102.zip
	 */
	public static final String GSModelDescriptor = GSModelArchive;
	
	/**
	 * Example of valid filename: N62W162_D303_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.xml
	 */
	public static final String GSModelDescriptorEntry = "^(?<lat>(S|N)[0-9]{2})(?<lon>(E|W)[0-9]{3})_D(?<datasetCode>[0-9]{3})_S(?<cs1>[0-9]{3})_T(?<cs2>[0-9]{3})_(?<lod>LC\\d{2}|L[0-9]{2})_(?<uref>U[0-9]+)_(?<rref>R[0-9]+)_(?<featureCode>.{5})_(?<fsc>\\d+)_(?<modl>[^.]+)\\.(?<ext>.+)$";
	
	/**
	 * Example of valid filename: N62W162_D300_S001_T001_L07_U38_R102.zip
	 */
	public static final String GSModelGeometry = GSModelArchive;
	
	/**
	 * Example of valid filename: N62W162_D300_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.flt
	 */
	public static final String GSModelGeometryEntry = "^(?<lat>(S|N)[0-9]{2})(?<lon>(E|W)[0-9]{3})_D(?<datasetCode>[0-9]{3})_S(?<cs1>[0-9]{3})_T(?<cs2>[0-9]{3})_(?<lod>LC\\d{2}|L[0-9]{2})_(?<uref>U[0-9]+)_(?<rref>R[0-9]+)_(?<featureCode>.{5})_(?<fsc>\\d+)_(?<modl>[^.]+)\\.(?<ext>.+)$";
	
	/**
	 * Example of valid filename: N62W162_D307_S001_T001_L07_U38_R102.zip
	 */
	public static final String GSModelInteriorDescriptor = GSModelArchive;
	
	/**
	 * Example of valid filename: N62W162_D307_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.xml
	 */
	public static final String GSModelInteriorDescriptorEntry = "^(?<lat>(S|N)[0-9]{2})(?<lon>(E|W)[0-9]{3})_D(?<datasetCode>[0-9]{3})_S(?<cs1>[0-9]{3})_T(?<cs2>[0-9]{3})_(?<lod>LC\\d{2}|L[0-9]{2})_(?<uref>U[0-9]+)_(?<rref>R[0-9]+)_(?<featureCode>.{5})_(?<fsc>\\d+)_(?<modl>[^.]+)\\.(?<ext>.+)$";
	
	/**
	 * Example of valid filename: N62W162_D305_S001_T001_L07_U38_R102.zip
	 */
	public static final String GSModelInteriorGeometry = GSModelArchive;
	
	/**
	 * Example of valid filename: N62W162_D305_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.flt
	 */
	public static final String GSModelInteriorGeometryEntry = "^(?<lat>(S|N)[0-9]{2})(?<lon>(E|W)[0-9]{3})_D(?<datasetCode>[0-9]{3})_S(?<cs1>[0-9]{3})_T(?<cs2>[0-9]{3})_(?<lod>LC\\d{2}|L[0-9]{2})_(?<uref>U[0-9]+)_(?<rref>R[0-9]+)_(?<featureCode>.{5})_(?<fsc>\\d+)_(?<modl>[^.]+)\\.(?<ext>.+)$";
	
	/**
	 * Example of valid filename: N62W162_D308_S001_T001_L07_U38_R102.zip
	 */
	public static final String GSModelInteriorMaterial = GSModelArchive;
	
	/**
	 * Example of valid filename: N62W162_D308_S001_T001_L07_U38_R102_AcmeFactory.tif
	 */
	public static final String GSModelInteriorMaterialEntry = "^(?<lat>(S|N)[0-9]{2})(?<lon>(E|W)[0-9]{3})_D(?<datasetCode>[0-9]{3})_S(?<cs1>[0-9]{3})_T(?<cs2>[0-9]{3})_(?<lod>LC\\d{2}|L[0-9]{2})_(?<uref>U[0-9]+)_(?<rref>R[0-9]+)_(?<tnam>[^.]+)\\.(?<ext>.+)$";
	
	/**
	 * Example of valid filename: N62W162_D306_S001_T001_L07_U38_R102.zip
	 */
	public static final String GSModelInteriorTexture = GSModelArchive;
	
	/**
	 * Example of valid filename: N62W162_D306_S001_T001_L07_U38_R102_AcmeFactoryWall.rgb
	 */
	public static final String GSModelInteriorTextureEntry = "^(?<lat>(S|N)[0-9]{2})(?<lon>(E|W)[0-9]{3})_D(?<datasetCode>[0-9]{3})_S(?<cs1>[0-9]{3})_T(?<cs2>[0-9]{3})_(?<lod>LC\\d{2}|L[0-9]{2})_(?<uref>U[0-9]+)_(?<rref>R[0-9]+)_(?<tnam>[^.]+)\\.(?<ext>.+)$";
	
	/**
	 * Example of valid filename: N62W162_D304_S001_T001_L07_U38_R102.zip
	 */
	public static final String GSModelMaterial = GSModelArchive;
	
	/**
	 * Example of valid filename: N62W162_D304_S001_T001_L07_U38_R102_AcmeFactory.tif
	 */
	public static final String GSModelMaterialEntry = "^(?<lat>(S|N)[0-9]{2})(?<lon>(E|W)[0-9]{3})_D(?<datasetCode>[0-9]{3})_S(?<cs1>[0-9]{3})_T(?<cs2>[0-9]{3})_(?<lod>LC\\d{2}|L[0-9]{2})_(?<uref>U[0-9]+)_(?<rref>R[0-9]+)_(?<tnam>[^.]+)\\.(?<ext>.+)$";

	/**
	 * Example of valid filename: N62W162_D301_S001_T001_L07_U38_R102.zip
	 */
	public static final String GSModelTexture = GSModelArchive;
	
	/**
	 * Example of valid filename: N62W162_D301_S001_T001_L07_U38_R102_AcmeFactory.rgb
	 */
	public static final String GSModelTextureEntry = "^(?<lat>(S|N)[0-9]{2})(?<lon>(E|W)[0-9]{3})_D(?<datasetCode>[0-9]{3})_S(?<cs1>[0-9]{3})_T(?<cs2>[0-9]{3})_(?<lod>LC\\d{2}|L[0-9]{2})_(?<uref>U[0-9]+)_(?<rref>R[0-9]+)_(?<tnam>[^.]+)\\.(?<ext>.+)$";

	
	/**
	 * Example of valid filename: D505_S001_T001_AC1.xml
	 */
	public static final String GTModelCMT = "^D505_S(?<cs1>\\d+)_T(?<cs2>\\d+)_(?<tnam>[^.]+)\\.(?<ext>.+)$";

	/**
	 * Example of valid filename: D503_S001_T001_12345_001_modelnamehere.xml
	 */
	public static final String GTModelDescriptor = "^D503_S(?<cs1>\\d+)_T(?<cs2>\\d+)_(?<featureCode>.{5})_(?<fsc>\\d+)_(?<modl>[^.]+)\\.(?<ext>.+)$";

	/**
	 * Example of valid filename: D500_S001_T001_12345_001_modelnamehere.flt
	 */
	public static final String GTModelGeometry = "^D500_S(?<cs1>\\d+)_T(?<cs2>\\d+)_(?<featureCode>.{5})_(?<fsc>\\d+)_(?<modl>[^.]+)\\.(?<ext>.+)$";

	/**
	 * Example of valid filename: D510_S001_T001_L10_12345_001_modelnamehere.flt
	 */
	public static final String GTModelGeometry510 = "^D510_S(?<cs1>\\d+)_T(?<cs2>\\d+)_(?<lod>LC\\d{2}|L\\d{2})_(?<featureCode>.{5})_(?<fsc>\\d+)_(?<modl>[^.]+)\\.(?<ext>.+)$";

	/**
	 * Example of valid filename: D508_S001_T001_12345_001_modelnamehere.xml
	 */
	public static final String GTModelInteriorDescriptor = "^D508_S(?<cs1>\\d+)_T(?<cs2>\\d+)_(?<featureCode>.{5})_(?<fsc>\\d+)_(?<modl>[^.]+)\\.(?<ext>.+)$";

	/**
	 * Example of valid filename: D506_S001_T001_L03_AL015_004_Castle.flt
	 */
	public static final String GTModelInteriorGeometry = "^D506_S(?<cs1>\\d+)_T(?<cs2>\\d+)_(?<lod>LC\\d{2}|L\\d{2})_(?<featureCode>.{5})_(?<fsc>\\d+)_(?<modl>[^.]+)\\.(?<ext>.+)$";

	/**
	 * Example of valid filename: D509_S001_T001_L10_AC_1.tif
	 */
	public static final String GTModelInteriorMaterial = "^D509_S(?<cs1>\\d+)_T(?<cs2>\\d+)_(?<lod>LC\\d{2}|L\\d{2})_(?<tnam>[^.]+)\\.(?<ext>.+)$";

	/**
	 * Example of valid filename: D507_S001_T001_L10_AC_1.rgb
	 */
	public static final String GTModelInteriorTexture = "^D507_S(?<cs1>\\d+)_T(?<cs2>\\d+)_(?<lod>LC\\d{2}|L\\d{2})_(?<tnam>[^.]+)\\.(?<ext>.+)$";

	/**
	 * Example of valid filename: D504_S001_T001_L10_AC_1.tif
	 */
	public static final String GTModelMaterial = "^D504_S(?<cs1>\\d+)_T(?<cs2>\\d+)_(?<lod>LC\\d{2}|L\\d{2})_(?<tnam>[^.]+)\\.(?<ext>.+)$";

	/**
	 * Example of valid filename: D512_S001_T001_L10_12345_001_modelnamehere.flt
	 */
	public static final String GTModelSignature = "^D512_S(?<cs1>\\d+)_T(?<cs2>\\d+)_(?<lod>LC\\d{2}|L\\d{2})_(?<featureCode>.{5})_(?<fsc>\\d+)_(?<modl>[^.]+)\\.(?<ext>.+)$";

	/**
	 * Example of valid filename: D511_S001_T001_L10_AC_1.rgb
	 */
	public static final String GTModelTexture = "^D511_S(?<cs1>\\d+)_T(?<cs2>\\d+)_(?<lod>LC\\d{2}|L\\d{2})_(?<tnam>[^.]+)\\.(?<ext>.+)$";

	/**
	 * Example of valid filename: D600_S001_T001_1_1_225_1_1_8_0.flt
	 */
	public static final String MModelGeometry = "^(?<dataset>D600|D603)_S(?<cs1>\\d+)_T(?<cs2>\\d+)_"
			+ "(?<mmdc>(?<kind>\\d+)_(?<domain>\\d+)_(?<country>\\d+)_(?<category>\\d+)_(\\d+)_(\\d+)_(\\d+))\\.(?<ext>.+)$";

	/**
	 * Example of valid filename: D606_S001_T001_LC02_0_0_0_0_0_0_0.shp
	 */
	public static final String MModelSignature = "^D606_S(?<cs1>\\d+)_T(?<cs2>\\d+)_(?<lod>LC\\d{2}|L\\d{2})_"
			+ "(?<mmdc>(?<kind>\\d+)_(?<domain>\\d+)_(?<country>\\d+)_(?<category>\\d+)_(\\d+)_(\\d+)_(\\d+))\\.(?<ext>.+)$";

	/**
	 * Example of valid filename: D601_S005_T001_W10_M1A2_SEP.rgb
	 */
	public static final String MModelTexture = "^(?<dataset>D601|D604|D605)_S(?<cs1>\\d+)_T(?<cs2>\\d+)"
			+ "(_W(?<tsc>\\d{2}))?_(?<tnam>[^.]+)\\.(?<ext>.+)$";

	/**
	 * Example of valid filename: D400_S001_T002.dbf
	 */
	public static final String NavigationLibrary = "^(?<dataset>[^_]+)_S(?<cs1>\\d+)_T(?<cs2>\\d+)\\.(?<ext>.+)$";
	
	public static final String Tiles = "^(?<lat>(S|N)[0-9]{2})(?<lon>(E|W)[0-9]{3})_D(?<datasetCode>[0-9]{3})_S(?<cs1>[0-9]{3})_T(?<cs2>[0-9]{3})_(?<lod>LC\\d{2}|L[0-9]{2})_(?<uref>U[0-9]+)_R(?<rref>[0-9]+)\\.(?<ext>.+)$";
}
