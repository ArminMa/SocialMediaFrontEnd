var app = angular.module('CarShop', ["angularfaces"]).
controller('CarShopController', function($scope, $filter) {
	// This initializes the Angular Model with the values of the JSF bean attributes
	initJSFScope($scope);
	$scope.activeSelections=0;

	$scope.showIfSet = function(ngmodel) {
		if (typeof(ngmodel)=='undefined') {
			return "hidden";
		}
		if (null==ngmodel || ngmodel=="") {
			return "hidden";
		}
		$scope.activeSelections++;
		return "";
	}
	
	$scope.filterBoxStyle=function(){
		var width=400+100*$scope.activeSelections;
		return "" + width + "px";
	}
	
	$scope.getTime = function() {
		 var temp = new Date();
		    var time = padStr(temp.getHours()) + ":" +
		                  padStr(temp.getMinutes()) + ":" +
		                  padStr(temp.getSeconds());
		    return time;
	}
})


function padStr(i) {
    return (i < 10) ? "0" + i : "" + i;
}

