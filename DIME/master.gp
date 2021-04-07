plugin MasterPiece {
	styles ["ExampleGUIPlugin/style.css"]
	scripts ["https://cdnjs.cloudflare.com/ajax/libs/Chart.js/0.2.0/Chart.min.js","ExampleGUIPlugin/plugin.js"]
	template "ExampleGUIPlugin/plugin.html"
	function showChart {
		primitiveParameter "value1" : integer sync
		primitiveParameter "value2" : real
		
		output finished {
			primitiveParameter "caluclated" :real
			primitiveParameter "fooo" : boolean
		}
	}
}