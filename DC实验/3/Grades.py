# -*- coding:utf-8 -*-

from pyspark import SparkConf, SparkContext


def func(x):
    if 90 <= x <= 100:
        return "90~100:", 1
    elif 80 <= x < 90:
        return "80~89:", 1
    elif 70 <= x < 80:
        return "70~79:", 1
    elif 60 <= x < 70:
        return "60~69:", 1
    else:
        return "<60:", 1


conf = SparkConf().setMaster("local").setAppName("grade")
sc = SparkContext(conf=conf)

textData = sc.textFile("/input/grades.txt")

bixiu = textData.filter(lambda line: "必修" in line)
bixiu = bixiu.map(lambda line: line.split(","))
bixiu = bixiu.map(lambda line: ((line[0]+" "+line[1]), int(line[4])))

avg_grades = bixiu.mapValues(lambda x: (x, 1)).reduceByKey(lambda x, y: (x[0]+y[0], x[1] + y[1]))  # 成绩求和
avg_grades = avg_grades.mapValues(lambda x: float(x[0] / x[1]))
avg_grades.saveAsTextFile("/result1")

grades_num = avg_grades.map(lambda line: func(line[1])).reduceByKey(lambda x, y: x + y)

grades_num.saveAsTextFile("/result2")
