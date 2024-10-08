package com.bigdatacompany.spark;

import com.bigdatacompany.spark.model.Person;
import com.google.common.collect.Iterables;
import org.apache.hadoop.security.SaslOutputStream;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

public class MapTrans {
    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "C:\\bigdata\\hadoop");

        JavaSparkContext sc = new JavaSparkContext("local", "Map Transformation Spark");
        JavaRDD<String> rawData = sc.textFile("C:\\Users\\Pantheon\\Desktop\\BigData\\Datasets\\person.csv");
        System.out.println("count: " + rawData.count());

      // -Distinct-
        /*JavaRDD<String> distData = rawData.distinct();
        System.out.println("distinct count: " + distData.count());*/

        // --FlatMap--
        /*JavaRDD<String> stringJavaRDD = rawData.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(",")).iterator();
            }
        });
        System.out.println(stringJavaRDD.count());*/

        // --Map--
        JavaRDD<Person> loadPerson = rawData.map(new Function<String, Person>() {
            @Override
            public Person call(String line) throws Exception {
                String[] data = line.split(",");
                Person p = new Person();
                p.setFirst_name(data[0]);
                p.setLast_name(data[1]);
                p.setEmail(data[2]);
                p.setGender(data[3]);
                p.setCountry(data[4]);
                return  p;
            }
        });

        /*loadPerson.foreach(new VoidFunction<Person>() {
            @Override
            public void call(Person person) throws Exception {
                System.out.println("first_name: " + person.getFirst_name()
                        + " - last_name: " + person.getLast_name());
            }
        });*/


        // --Filter--
        /*JavaRDD<Person> personFromCanada = loadPerson.filter(new Function<Person, Boolean>() {
            @Override
            public Boolean call(Person person) throws Exception {
                return person.getCountry().equals("Canada") && person.getGender().equals("Male");
            }
        });
        System.out.println("Person Count: " + personFromCanada.count());

        personFromCanada.foreach(new VoidFunction<Person>() {
            @Override
            public void call(Person person) throws Exception {
                System.out.println(person.getFirst_name() + " " + person.getLast_name()
                        + " - " + person.getCountry() + " - " + person.getGender());
            }
        });*/


        // --MapToPair--
        /*JavaPairRDD<String, String> pairRdd = loadPerson.mapToPair(new PairFunction<Person, String, String>() {
            @Override
            public Tuple2<String, String> call(Person person) throws Exception {
                return new Tuple2<String, String>(person.getEmail(), person.getCountry());
            }
        });
        pairRdd.foreach(new VoidFunction<Tuple2<String, String>>() {
            @Override
            public void call(Tuple2<String, String> data) throws Exception {
                System.out.println("Email: " + data._1 + " - Country: " + data._2);
            }
        });*/

        /*JavaPairRDD<String, Person> pairRdd = loadPerson.mapToPair(new PairFunction<Person, String, Person>() {
            @Override
            public Tuple2<String, Person> call(Person person) throws Exception {
                return new Tuple2<String, Person>(person.getCountry(), person);
            }
        });

        JavaPairRDD<String, Iterable<Person>> groupedData = pairRdd.groupByKey();
        groupedData.foreach(new VoidFunction<Tuple2<String, Iterable<Person>>>() {
            @Override
            public void call(Tuple2<String, Iterable<Person>> data) throws Exception {
                System.out.println("Key : " + data._1+" Count : " + Iterables.size(data._2));
            }
        });*/



    }
}
