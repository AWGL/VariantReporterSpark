package nhs.genetics.cardiff.filters;

import htsjdk.variant.variantcontext.Allele;
import htsjdk.variant.variantcontext.GenotypeBuilder;
import htsjdk.variant.variantcontext.VariantContextBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by ml on 16/06/2017.
 */
public class FunctionalConsequenceSparkFilterTest {

    private final static String[] headers = "Allele|Consequence|IMPACT|SYMBOL|Gene|Feature_type|Feature|BIOTYPE|EXON|INTRON|HGVSc|HGVSp|cDNA_position|CDS_position|Protein_position|Amino_acids|Codons|Existing_variation|ALLELE_NUM|DISTANCE|STRAND|FLAGS|VARIANT_CLASS|SYMBOL_SOURCE|HGNC_ID|CANONICAL|TSL|APPRIS|CCDS|ENSP|SWISSPROT|TREMBL|UNIPARC|REFSEQ_MATCH|GENE_PHENO|SIFT|PolyPhen|DOMAINS|HGVS_OFFSET|GMAF|AFR_MAF|AMR_MAF|EAS_MAF|EUR_MAF|SAS_MAF|AA_MAF|EA_MAF|ExAC_MAF|ExAC_Adj_MAF|ExAC_AFR_MAF|ExAC_AMR_MAF|ExAC_EAS_MAF|ExAC_FIN_MAF|ExAC_NFE_MAF|ExAC_OTH_MAF|ExAC_SAS_MAF|CLIN_SIG|SOMATIC|PHENO|PUBMED|MOTIF_NAME|MOTIF_POS|HIGH_INF_POS|MOTIF_SCORE_CHANGE".trim().split("\\|");
    private final static String keep = "-|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204184.1|protein_coding||3/12|NM_001204184.1:c.186+7delG|||||||rs57492244&TMP_ESP_1_3599751_3599751|1||1||sequence_alteration|||||||NP_001191113.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,GG|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204184.1|protein_coding||3/12|NM_001204184.1:c.186+7dupG|||||||rs57492244&TMP_ESP_1_3599751_3599751|2||1||sequence_alteration|||||||NP_001191113.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,-|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204184.1|protein_coding||3/12|NM_001204184.1:c.186+7delG|||||||rs57492244&TMP_ESP_1_3599751_3599751|1||1||sequence_alteration|||||||NP_001191113.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,GG|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204184.1|protein_coding||3/12|NM_001204184.1:c.186+7dupG|||||||rs57492244&TMP_ESP_1_3599751_3599751|2||1||sequence_alteration|||||||NP_001191113.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,-|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204185.1|protein_coding||3/12|NM_001204185.1:c.186+7delG|||||||rs57492244&TMP_ESP_1_3599751_3599751|1||1||sequence_alteration|||||||NP_001191114.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,GG|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204185.1|protein_coding||3/12|NM_001204185.1:c.186+7dupG|||||||rs57492244&TMP_ESP_1_3599751_3599751|2||1||sequence_alteration|||||||NP_001191114.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,-|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204185.1|protein_coding||3/12|NM_001204185.1:c.186+7delG|||||||rs57492244&TMP_ESP_1_3599751_3599751|1||1||sequence_alteration|||||||NP_001191114.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,GG|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204185.1|protein_coding||3/12|NM_001204185.1:c.186+7dupG|||||||rs57492244&TMP_ESP_1_3599751_3599751|2||1||sequence_alteration|||||||NP_001191114.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,-|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204186.1|protein_coding||3/10|NM_001204186.1:c.186+7delG|||||||rs57492244&TMP_ESP_1_3599751_3599751|1||1||sequence_alteration|||||||NP_001191115.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,GG|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204186.1|protein_coding||3/10|NM_001204186.1:c.186+7dupG|||||||rs57492244&TMP_ESP_1_3599751_3599751|2||1||sequence_alteration|||||||NP_001191115.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,-|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204186.1|protein_coding||3/10|NM_001204186.1:c.186+7delG|||||||rs57492244&TMP_ESP_1_3599751_3599751|1||1||sequence_alteration|||||||NP_001191115.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,GG|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204186.1|protein_coding||3/10|NM_001204186.1:c.186+7dupG|||||||rs57492244&TMP_ESP_1_3599751_3599751|2||1||sequence_alteration|||||||NP_001191115.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,-|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204187.1|protein_coding||3/11|NM_001204187.1:c.186+7delG|||||||rs57492244&TMP_ESP_1_3599751_3599751|1||1||sequence_alteration|||||||NP_001191116.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,GG|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204187.1|protein_coding||3/11|NM_001204187.1:c.186+7dupG|||||||rs57492244&TMP_ESP_1_3599751_3599751|2||1||sequence_alteration|||||||NP_001191116.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,-|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204187.1|protein_coding||3/11|NM_001204187.1:c.186+7delG|||||||rs57492244&TMP_ESP_1_3599751_3599751|1||1||sequence_alteration|||||||NP_001191116.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,GG|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204187.1|protein_coding||3/11|NM_001204187.1:c.186+7dupG|||||||rs57492244&TMP_ESP_1_3599751_3599751|2||1||sequence_alteration|||||||NP_001191116.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,-|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204188.1|protein_coding||3/11|NM_001204188.1:c.186+7delG|||||||rs57492244&TMP_ESP_1_3599751_3599751|1||1||sequence_alteration|||||||NP_001191117.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,GG|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204188.1|protein_coding||3/11|NM_001204188.1:c.186+7dupG|||||||rs57492244&TMP_ESP_1_3599751_3599751|2||1||sequence_alteration|||||||NP_001191117.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,-|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204188.1|protein_coding||3/11|NM_001204188.1:c.186+7delG|||||||rs57492244&TMP_ESP_1_3599751_3599751|1||1||sequence_alteration|||||||NP_001191117.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,GG|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_001204188.1|protein_coding||3/11|NM_001204188.1:c.186+7dupG|||||||rs57492244&TMP_ESP_1_3599751_3599751|2||1||sequence_alteration|||||||NP_001191117.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,-|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_005427.3|protein_coding||3/13|NM_005427.3:c.186+7delG|||||||rs57492244&TMP_ESP_1_3599751_3599751|1||1||sequence_alteration|||YES||||NP_005418.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,GG|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_005427.3|protein_coding||3/13|NM_005427.3:c.186+7dupG|||||||rs57492244&TMP_ESP_1_3599751_3599751|2||1||sequence_alteration|||YES||||NP_005418.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,-|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_005427.3|protein_coding||3/13|NM_005427.3:c.186+7delG|||||||rs57492244&TMP_ESP_1_3599751_3599751|1||1||sequence_alteration|||YES||||NP_005418.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,GG|splice_region_variant&intron_variant|LOW|TP73|7161|Transcript|NM_005427.3|protein_coding||3/13|NM_005427.3:c.186+7dupG|||||||rs57492244&TMP_ESP_1_3599751_3599751|2||1||sequence_alteration|||YES||||NP_005418.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,-|intron_variant|MODIFIER|TP73|7161|Transcript|XM_005244779.1|protein_coding||2/12|XM_005244779.1:c.180+13delG|||||||rs57492244&TMP_ESP_1_3599751_3599751|1||1||sequence_alteration|||||||XP_005244836.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,GG|intron_variant|MODIFIER|TP73|7161|Transcript|XM_005244779.1|protein_coding||2/12|XM_005244779.1:c.180+13dupG|||||||rs57492244&TMP_ESP_1_3599751_3599751|2||1||sequence_alteration|||||||XP_005244836.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,-|intron_variant|MODIFIER|TP73|7161|Transcript|XM_005244779.1|protein_coding||2/12|XM_005244779.1:c.180+13delG|||||||rs57492244&TMP_ESP_1_3599751_3599751|1||1||sequence_alteration|||||||XP_005244836.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||,GG|intron_variant|MODIFIER|TP73|7161|Transcript|XM_005244779.1|protein_coding||2/12|XM_005244779.1:c.180+13dupG|||||||rs57492244&TMP_ESP_1_3599751_3599751|2||1||sequence_alteration|||||||XP_005244836.1||||||||||-:0.1677||||||||-:0.100&GG:6.838e-04&-:0.100&GG:6.838e-04|-:0.1007&GG:0.0006714&-:0.1007&GG:0.0006714|-:0.3014&GG:9.684e-05&-:0.3014&GG:9.684e-05|-:0.08756&GG:0.0005197&-:0.08756&GG:0.0005197|-:0.1539&GG:0&-:0.1539&GG:0|-:0.1176&GG:0&-:0.1176&GG:0|-:0.05697&GG:0.001082&-:0.05697&GG:0.001082|-:0.0871&GG:0&-:0.0871&GG:0|-:0.1259&GG:0.0001287&-:0.1259&GG:0.0001287||||||||";

    private static String bin = "G|intron_variant|MODIFIER|MCPH1|79648|Transcript|NM_024596.3|protein_coding||12/13|NM_024596.3:c.2215-19A>G|||||||rs2936531|1||1||SNV|||||||NP_078872.2||||||||||G:0.4904|G:0.4554|G:0.366|G:0.6835|G:0.4235|G:0.4959|G:0.4542|G:0.4058|G:0.448|G:0.4492|G:0.4748|G:0.4031|G:0.6766|G:0.4479|G:0.4107|G:0.4531|G:0.5032|||1|||||,G|intron_variant|MODIFIER|MCPH1|79648|Transcript|NM_024596.3|protein_coding||12/13|NM_024596.3:c.2215-19A>G|||||||rs2936531|1||1||SNV|||||||NP_078872.2||||||||||G:0.4904|G:0.4554|G:0.366|G:0.6835|G:0.4235|G:0.4959|G:0.4542|G:0.4058|G:0.448|G:0.4492|G:0.4748|G:0.4031|G:0.6766|G:0.4479|G:0.4107|G:0.4531|G:0.5032|||1|||||,G|intron_variant|MODIFIER|MCPH1|79648|Transcript|XM_005266034.1|protein_coding||12/13|XM_005266034.1:c.2215-19A>G|||||||rs2936531|1||1||SNV|||YES||||XP_005266091.1||||||||||G:0.4904|G:0.4554|G:0.366|G:0.6835|G:0.4235|G:0.4959|G:0.4542|G:0.4058|G:0.448|G:0.4492|G:0.4748|G:0.4031|G:0.6766|G:0.4479|G:0.4107|G:0.4531|G:0.5032|||1|||||,G|intron_variant|MODIFIER|MCPH1|79648|Transcript|XM_005266034.1|protein_coding||12/13|XM_005266034.1:c.2215-19A>G|||||||rs2936531|1||1||SNV|||YES||||XP_005266091.1||||||||||G:0.4904|G:0.4554|G:0.366|G:0.6835|G:0.4235|G:0.4959|G:0.4542|G:0.4058|G:0.448|G:0.4492|G:0.4748|G:0.4031|G:0.6766|G:0.4479|G:0.4107|G:0.4531|G:0.5032|||1|||||,G|intron_variant|MODIFIER|MCPH1|79648|Transcript|XM_005266035.1|protein_coding||11/12|XM_005266035.1:c.1060-19A>G|||||||rs2936531|1||1||SNV|||||||XP_005266092.1||||||||||G:0.4904|G:0.4554|G:0.366|G:0.6835|G:0.4235|G:0.4959|G:0.4542|G:0.4058|G:0.448|G:0.4492|G:0.4748|G:0.4031|G:0.6766|G:0.4479|G:0.4107|G:0.4531|G:0.5032|||1|||||,G|intron_variant|MODIFIER|MCPH1|79648|Transcript|XM_005266035.1|protein_coding||11/12|XM_005266035.1:c.1060-19A>G|||||||rs2936531|1||1||SNV|||||||XP_005266092.1||||||||||G:0.4904|G:0.4554|G:0.366|G:0.6835|G:0.4235|G:0.4959|G:0.4542|G:0.4058|G:0.448|G:0.4492|G:0.4748|G:0.4031|G:0.6766|G:0.4479|G:0.4107|G:0.4531|G:0.5032|||1|||||,G|upstream_gene_variant|MODIFIER||100507530|Transcript|XR_132725.2|misc_RNA||||||||||rs2936531|1|4243|-1||SNV|||YES||||||||||||||G:0.4904|G:0.4554|G:0.366|G:0.6835|G:0.4235|G:0.4959|G:0.4542|G:0.4058|G:0.448|G:0.4492|G:0.4748|G:0.4031|G:0.6766|G:0.4479|G:0.4107|G:0.4531|G:0.5032|||1|||||";

    @Test
    public void pass() throws Exception {
        GenotypeBuilder genotypeBuilder = new GenotypeBuilder();

        VariantContextBuilder variantContextBuilder = new VariantContextBuilder("test", "1", 3599750, 3599751, Arrays.asList(Allele.create("TG", true), Allele.create("T", false), Allele.create("TGG", false), Allele.create("TC", false)));
        variantContextBuilder.attribute("AC", new int[]{1});
        variantContextBuilder.attribute("GNOMAD_2.0.1_Genome_chr1.AF_POPMAX", new double[]{0.0001});
        variantContextBuilder.attribute("GNOMAD_2.0.1_Exome.AF_POPMAX", new double[]{0.00001});
        variantContextBuilder.attribute("CSQ", new ArrayList(Arrays.asList(keep.split(","))));
        variantContextBuilder.unfiltered();

        variantContextBuilder.genotypes(
                genotypeBuilder.name("sample")
                        .alleles(Arrays.asList(Allele.create("TG", true), Allele.create("T", false)))
                        .unfiltered()
                        .make()
        );

        assertEquals(true, new FunctionalConsequenceSparkFilter("sample", headers, true).call(variantContextBuilder.make()));
    }
    @Test
    public void noCsq() throws Exception {
        GenotypeBuilder genotypeBuilder = new GenotypeBuilder();

        VariantContextBuilder variantContextBuilder = new VariantContextBuilder("test", "1", 3599750, 3599751, Arrays.asList(Allele.create("TG", true), Allele.create("T", false), Allele.create("TGG", false), Allele.create("TC", false)));
        variantContextBuilder.attribute("AC", new int[]{1});
        variantContextBuilder.attribute("GNOMAD_2.0.1_Genome_chr1.AF_POPMAX", new double[]{0.0001});
        variantContextBuilder.attribute("GNOMAD_2.0.1_Exome.AF_POPMAX", new double[]{0.00001});
        variantContextBuilder.unfiltered();

        variantContextBuilder.genotypes(
                genotypeBuilder.name("sample")
                        .alleles(Arrays.asList(Allele.create("TG", true), Allele.create("T", false)))
                        .unfiltered()
                        .make()
        );

        assertEquals(false, new FunctionalConsequenceSparkFilter("sample", headers, true).call(variantContextBuilder.make()));
    }
    @Test
    public void allAllelesRef() throws Exception {
        GenotypeBuilder genotypeBuilder = new GenotypeBuilder();

        VariantContextBuilder variantContextBuilder = new VariantContextBuilder("test", "1", 3599750, 3599751, Arrays.asList(Allele.create("TG", true), Allele.create("T", false), Allele.create("TGG", false), Allele.create("TC", false)));
        variantContextBuilder.attribute("AC", new int[]{1});
        variantContextBuilder.attribute("GNOMAD_2.0.1_Genome_chr1.AF_POPMAX", new double[]{0.0001});
        variantContextBuilder.attribute("GNOMAD_2.0.1_Exome.AF_POPMAX", new double[]{0.00001});
        variantContextBuilder.attribute("CSQ", new ArrayList(Arrays.asList(keep.split(","))));
        variantContextBuilder.unfiltered();

        variantContextBuilder.genotypes(
                genotypeBuilder.name("sample")
                        .alleles(Arrays.asList(Allele.create("TG", true), Allele.create("TG", true)))
                        .unfiltered()
                        .make()
        );

        assertEquals(false, new FunctionalConsequenceSparkFilter("sample", headers, true).call(variantContextBuilder.make()));
    }
    @Test
    public void noAnnotatedAllele() throws Exception {
        GenotypeBuilder genotypeBuilder = new GenotypeBuilder();

        VariantContextBuilder variantContextBuilder = new VariantContextBuilder("test", "1", 3599750, 3599751, Arrays.asList(Allele.create("TG", true), Allele.create("T", false), Allele.create("TGG", false), Allele.create("TC", false)));
        variantContextBuilder.attribute("AC", new int[]{1});
        variantContextBuilder.attribute("GNOMAD_2.0.1_Genome_chr1.AF_POPMAX", new double[]{0.0001});
        variantContextBuilder.attribute("GNOMAD_2.0.1_Exome.AF_POPMAX", new double[]{0.00001});
        variantContextBuilder.attribute("CSQ", new ArrayList(Arrays.asList(keep.split(","))));
        variantContextBuilder.unfiltered();

        variantContextBuilder.genotypes(
                genotypeBuilder.name("sample")
                        .alleles(Arrays.asList(Allele.create("TG", true), Allele.create("TC", false)))
                        .unfiltered()
                        .make()
        );

        assertEquals(false, new FunctionalConsequenceSparkFilter("sample", headers, true).call(variantContextBuilder.make()));
    }
    @Test
    public void noSignificantConsequence() throws Exception {
        GenotypeBuilder genotypeBuilder = new GenotypeBuilder();

        VariantContextBuilder variantContextBuilder = new VariantContextBuilder("test", "8", 6478956, 6478956, Arrays.asList(Allele.create("A", true), Allele.create("G", false)));
        variantContextBuilder.attribute("AC", new int[]{1});
        variantContextBuilder.attribute("GNOMAD_2.0.1_Genome_chr1.AF_POPMAX", new double[]{0.0001});
        variantContextBuilder.attribute("GNOMAD_2.0.1_Exome.AF_POPMAX", new double[]{0.00001});
        variantContextBuilder.attribute("CSQ", new ArrayList(Arrays.asList(bin.split(","))));
        variantContextBuilder.unfiltered();

        variantContextBuilder.genotypes(
                genotypeBuilder.name("sample")
                        .alleles(Arrays.asList(Allele.create("A", true), Allele.create("G", false)))
                        .unfiltered()
                        .make()
        );

        assertEquals(false, new FunctionalConsequenceSparkFilter("sample", headers, true).call(variantContextBuilder.make()));
    }

}