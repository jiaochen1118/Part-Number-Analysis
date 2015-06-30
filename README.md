# Part Number Analysis
## 1.Data Collection and Clean
## 2.Part Number matching
  Provide a fast part number matching lookup with compressed data structure. It supports exact part number match and prefix match.<br />
  **Install**: <br />
    Use "pip install dawg" to install dawg package in python.<br />
    Then, download part number matching from HDFS. Use "hadoop fs -get /user/jchen/IHS_partnumber_dawg.p" to download.<br />
  **Usage**:<br />
    In python, you can try:
    
    ```
    import pickle
    import dawg
    with open("IHS_partsio.p") as f_open :
         IHS_partnumber = pickle.load(f_open)
    ```
