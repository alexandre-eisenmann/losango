losango.controller('MasterController', ['$scope', function($scope) {

    $scope.form = '';

    $scope.validationFeedback = function(element) {
        var result;
        if ($scope.form.$submitted || element.$touched) {
            //result = 'has-feedback';
            if (element.$invalid && ($scope.form.$submitted || element.$touched)) {
                result = 'has-feedback';
                result = result + ' has-error'
            }
            //if (element.$valid){
            //    result = result + ' has-success'
            //}
        }
        return result;
    };

    $scope.showError = function(element) {
        var result = false;
        if ((element.$invalid) && ($scope.form.$submitted || element.$touched)) {
            result = true;
        }
        console.log("Error == " + result);
        return result;
    };

    $scope.isFormInvalid = function() {
        console.log($scope.form.$invalid);
        return $scope.form.$invalid;
    }

}]);