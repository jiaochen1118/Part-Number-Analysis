package WordCount.WordCount;

import java.io.IOException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCount extends Configured implements Tool {
    
    private String outputFile;
    private String inputFile;

    public WordCount(String outputFile, String inputFile) {
        this.outputFile = outputFile;
        this.inputFile = inputFile;
    }
    
    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();

        Job job = new Job(conf, "wcount");
        job.setJarByClass(WordCount.class);
        
        // mapper key, value
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(inputFile));
        FileOutputFormat.setOutputPath(job, new Path(outputFile));

        // reducer key, value
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        job.setMapperClass(wcountMapper.class);
        job.setReducerClass(LineReducer.class);
        
        //job.setOUtputValueGroupingComparator(GroupComparator.class);
        job.setNumReduceTasks(1);
        int status = job.waitForCompletion(true) ? 0 : 1;
        return status;
    }    
    
    private static class wcountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        Text outputKey = new Text();
        //Text outputValue = new Text();
        
        @Override
        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {

           // context.getCounter(this.getClass().getSimpleName(), "START_MAPPER").increment(1);
            
            String line = null;
            try {
                line = StringUtils.trimToEmpty(value.toString());
            } catch (Exception e) {
                context.getCounter(this.getClass().getSimpleName(), "LINE_TO_STRING_ERROR").increment(1);
                return;
            }
            
            // set key, value
            outputKey.set(line);
            //utputValue.set();
            
            context.write(outputKey, one);             
            //context.getCounter(this.getClass().getSimpleName(), "MAPPER_RECORD").increment(1);
               
        }
    }
    
    
    private static class LineReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
                
        IntWritable outputValue = new IntWritable();
        
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            /*context.getCounter(getClass().getSimpleName(), "START_REDUCER").increment(1);
         */   
            // set value
        	int sum = 0;
        	for (IntWritable val : values){
        		sum += val.get();
        	}
            outputValue.set(sum);
            context.write(key, outputValue);
        }
    }
    

    /**
     * hadoop jar botfilter.jar com.supplyframe.botfilter.WordCount -o /user/echen/wc/output1 -i /user/echen/wc/text
     * */
    public static void main(String[] args) throws Exception {
        
        Options options = new Options();
        options.addOption("o", "output", true, "the path for result data");
        options.addOption("i", "input", true, "the input source path");
        
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);
        
        if (!cmd.hasOption("o") || !cmd.hasOption("i")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("com.supplyframe.WordCount", options);
            return;
        }
        
        String outputFile = cmd.getOptionValue("o");
        String inputFile = cmd.getOptionValue("i");

        ToolRunner.run(new WordCount(outputFile, inputFile), new String[] {});
    }
}
