import java.io.IOException;
import java.util.StringTokenizer;

import java.util.ArrayList;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class classGrade {
 
	public static class MyMapper extends Mapper<Object, Text, Text, Text> {
		Text out_key = new Text();
		Text out_value = new Text();
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			String [] line = value.toString().trim().split(",");
			String mykey = line[2]+" "+line[0];
			String myvalue = line[4];
			out_key.set(mykey);
			out_value.set(myvalue);
			context.write(out_key, out_value);
		}
	}
 
	public static class MyReducer extends Reducer<Text, Text, Text, Text> {
		Text out_value = new Text();
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			int sum= 0;
			int count= 0;
			double avgscore = 0;
			for(Text text : values){
				int score = Integer.parseInt(text.toString());
				sum += score;
				count++;
			}
			avgscore = sum*1.0/ count;
			String myvalue = "Æ½¾ùÖµ = "+avgscore;
			out_value.set(myvalue);
			context.write(key, out_value);
		}
	}
 
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
 
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

		Job job = new Job(conf,"1.2");
		job.setJarByClass(classGrade.class);
		job.setMapperClass(MyMapper.class);
		job.setReducerClass(MyReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
 
		Path path = new Path(otherArgs[1]);
 
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}	
}