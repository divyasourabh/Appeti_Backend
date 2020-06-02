
<script src="https://apis.google.com/js/platform.js?onload=renderButton"
	async defer></script>
<meta name="google-signin-scope" content="profile email">
<meta name="google-signin-client_id"
	content="31636390776-dvcb0tafhsvrgclcv2ssugkskh6f9m2g.apps.googleusercontent.com">
<script>
    function onSuccess(googleUser) {
    	var profile = googleUser.getBasicProfile();
        var id_token = googleUser.getAuthResponse().id_token;
        soclogin(profile.getName(),'',profile.getEmail(),id_token);
    }
    function onFailure(error) {
      console.log(error);
    }
    function renderButton() {
      gapi.signin2.render('my-signin2', {
        'scope': 'https://www.googleapis.com/auth/plus.login',
        'width': 74,
        'height': 25,
        'longtitle': false,
        'theme': 'dark',
        'onsuccess': onSuccess,
        'onfailure': onFailure
      });
      gapi.load('auth2', function() {
          gapi.auth2.init();
        });
    }
  </script>

<script>
function signOut() {
	//var auth2 = gapi.auth2.getAuthInstance();
    //auth2.signOut().then(function () {
    //});
    window.location.replace("${pageContext.request.contextPath}/logout");
  }
</script>
