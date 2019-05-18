package com.github.mrpowers.spark.daria.sql.udafs

import utest._
import com.github.mrpowers.spark.daria.sql.SparkSessionExt._
import com.github.mrpowers.spark.daria.sql.SparkSessionTestWrapper
import com.github.mrpowers.spark.fast.tests.DataFrameComparer
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._


object ArraySumTest extends TestSuite with DataFrameComparer with SparkSessionTestWrapper {

  val tests = Tests {

    'arraySum - {
      "concatenates rows of arrays" - {

        val arraySum =  new ArraySum(StringType)
        val actualDF = spark
          .createDF(
            List(
              Array("snake", "rat"),
              null,
              Array("cat", "crazy")
            ),
            List(("array", ArrayType(StringType), true))
          ).agg(arraySum(col("array")).as("array"))

        val expectedDF = spark
          .createDF(
            List(Array("snake", "rat", "cat", "crazy")),
            List(("array", ArrayType(StringType), true))
          )

        assertSmallDataFrameEquality(
          actualDF,
          expectedDF
        )
      }
    }
  }

}

