Tika similarity and D3.js visualization

Follow the commands given below to run Tika Similarity and visualization using D3.js library.

1. After crawling run the commands
   
<path to nutch\runtime\local>/bin/nutch -mimetype image/jpeg -outputDir segment_images/ -segment <path to crawl folder>/segments \segments

The above command will create a folder with the name segment_images containing all images extracted from your crawl segments.

2. Install Tika-Python 

pip install tika

This command will install Tika in your system.

3. Installing tika-img-similarity repository

git clone https://github.com/chrismattmann/tika-img-similarity

This command will create a folder with the name tika-img-similarity in your current folder

4. Running Tika Similarity :

cd tika-img-similarity
python similarity.py -f [directory of files]

The directory for files in the above command is the folder containing images obtained from the command 1. 
The two above commands will generate a tika-similarity jar in your temp memory.

5. D3 Visualization : Creating and visualizing Clusters in d3

5a.Cluster Viz:
   
python cluster-scores.py (for generating cluster viz)  

The command will generate a cluster.json file which is needed for the visualization of d3. You can visualize the cluster by opening cluster-d3.html in your browser.

5b.Ciclepacking Viz:
  
python circle-packing.py (for generating circlepacking viz)

The command will generate a circlepacking.json file which is needed for the visualization of d3. You can visualize the cluster by opening circlepacking-d3.html in your browser.

5c. Composite Viz:

You can just open compositeViz.html in your browser.


