package edu.depaul.abuob.hadoop;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/* 
 * To define a reduce function for your MapReduce job, subclass 
 * the Reducer class and override the reduce method.
 * The class definition requires four parameters: 
 *   The data type of the input key (which is the output key type 
 *   from the mapper)
 *   The data type of the input value (which is the output value 
 *   type from the mapper)
 *   The data type of the output key
 *   The data type of the output value
 */
public class AvgLengthReducer extends Reducer<Text, IntWritable, Text, DoubleWritable> {

    /*
     * The reduce method runs once for each key received from
     * the shuffle and sort phase of the MapReduce framework.
     * The method receives a key of type Text, a set of values of type
     * IntWritable, and a Context object.
     */
    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {


        int count = 0;
        double wordLengthTotal = 0;
        double avgLength;

		/*
         * For each value in the set of values passed to us by the mapper:
		 */
        for (IntWritable value : values) {

		  /*
           * Add the value to the word count counter for this key.
		   */
            wordLengthTotal += (double) value.get();
            count++;
        }

        avgLength = wordLengthTotal / count;
		
		/*
		 * Call the write method on the Context object to emit a key
		 * and a value from the reduce method. 
		 */
        context.write(key, new DoubleWritable(avgLength));
    }
}