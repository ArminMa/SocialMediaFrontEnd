var app = angular.module('CarShop', [ "angularfaces" ]).controller(
		'CarShopController', function($scope) {
			// This initializes the Angular Model with the values of the JSF
			// bean attributes
			initJSFScope($scope);
			
			$scope.activateAJAXButton = function() {
				if ($scope.settingsBean.updateImmediately)
					return false;
				else
					return true;
			};
			
			$scope.sendFilterToServer = function(newValue, oldValue) {
				if ($scope.settingsBean.updateImmediately) {
					if (newValue != oldValue) {
						try {
							$scope.afSendNGSyncToServer();
						} catch (e) {
							console.log("Ein Fehler ist aufgetreten: " + e);
						}
					}
				}
			};

			$scope.$watch('filterBean.brand', $scope.sendFilterToServer);
			$scope.$watch('filterBean.type', $scope.sendFilterToServer);
			$scope.$watch('filterBean.price', $scope.sendFilterToServer);
			$scope.$watch('filterBean.mileage', $scope.sendFilterToServer);
			$scope.$watch('filterBean.fuel', $scope.sendFilterToServer);
			$scope.$watch('filterBean.color', $scope.sendFilterToServer);
			$scope.$watch('filterBean.yearText', $scope.sendFilterToServer);
		})


