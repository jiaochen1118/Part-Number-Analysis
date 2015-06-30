# Part Number Analysis
## 1.Data Collection and Clean
## 2.Part Number matching
  Provide a fast part number matching lookup with compressed data structure. It supports exact part number match and prefix match.<br />
  **Install**: <br />
    Use "pip install dawg" to install dawg package in python.<br />
    Then, download part number matching from HDFS. Use "hadoop fs -get /user/jchen/IHS_partnumber_dawg.p" to download.<br />
  **Usage**:<br />
    In python, run the following:
    
    ```
    import pickle
    import dawg
    with open("IHS_partnumber_dawg.p") as f_open :
        IHS_partnumber = pickle.load(f_open)
    ```
 
 You can Try the following to check if the term is exact partnumber of prefix:
   
   ```
   IHS_partnumber.has_key(u"MAX232DR")
   IHS_partnumber.has_keys_with_prefix(u"MAX232")
   ```
 
or see the [usage](http://dawg.readthedocs.org/en/latest/).
