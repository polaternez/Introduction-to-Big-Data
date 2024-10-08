package com.bigdatacompany.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

public class SparkProductExam {
    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "C:\\bigdata\\hadoop");

        SparkSession spark = SparkSession.builder()
                .master("local")
                .appName("First Exam")
                .getOrCreate();

        StructType schema = new StructType()
                .add("first_name", DataTypes.StringType)
                .add("last_name", DataTypes.StringType)
                .add("email", DataTypes.StringType)
                .add("country", DataTypes.StringType)
                .add("price", DataTypes.DoubleType)
                .add("product", DataTypes.StringType);
        Dataset<Row> productDF = spark.read()
                .option("multiline", true)
                .schema(schema)
                .json("C:\\Users\\Pantheon\\Desktop\\BigData\\Datasets\\product.json");

        /*Dataset<Row> countDF = productDF.groupBy("country", "product").count()
                .sort(functions.desc("count"));
        countDF.show();*/

        /*Dataset<Row> sumPriceDF = productDF.groupBy("country").sum("price")
                .sort(functions.desc("sum(price)"));
        sumPriceDF.show();*/

        /*Dataset<Row> avgPriceDF = productDF.groupBy("product").avg("price")
                .sort(functions.desc("avg(price)"));
        avgPriceDF.show(false);*/

        //--SQL API--
        productDF.createOrReplaceTempView("product");
//        productDF.createOrReplaceGlobalTempView("product");

        Dataset<Row> sqlDF = spark.sql(
                "select first_name, email, country from product where country='France' or country='China'"
        );
        sqlDF.show();



    }

}
