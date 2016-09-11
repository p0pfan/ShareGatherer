$(document).ready(function(){
	$.ajax({
		url: basePath+'/share/all',
		type: 'GET',
		dataType: 'json',
		success: function(data){
			insert(data);
		}
	});
})

function insert(data){
	for(var i =0; i< data.length; i++ ){
		var sharer = "cc"
		var div1 = '<div class="jumbotron" id = "'+sharer+"_"+'">';
		var div2 = '<div class="row"><div class="col-md-2">' + sharer + '</div>';
		var div3 = '<div class="col-md-2 col-md-offset-8">' +  '</div></div>';
		var p = '<p>' + data[i].content +'</p>';
		var labelspan = '';
		var ll = data[i].labels;
		for(var j = 0; j < ll.length; j++){
			labelspan += '<span class="label label-info">' + ll[j] +'</span>';
		}
		var template = div1 + div2 + div3 + p + labelspan;
		console.log(template);
		
		$("#content").append(template);
	}
}
