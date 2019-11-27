<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
<#if section = "title">
${msg("loginTitle",realm.name)}
<#elseif section = "header">
${msg("loginTitleHtml",realm.name)}
<#elseif section = "form">
<form id="kc-totp-login-form" class="form-signin-puk" action="${url.loginAction}" method="post">
    <img class="mb-4" src="http://www.animalhealthsurveillance.agriculture.gov.ie/media/animalhealthsurveillance/styleassets/images/DAFMLogo2018.png" width="300" height="100">
    <h1 class="h3 mb-3 font-weight-normal" style="text-align: center">PUK Sign in</h1>
    <div class="form-label-group">
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" >
            <label  class="form-control no-border padding-1">Please enter PUK number</label>

        </div>


        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" >

            <#list inputArray as item>


            <#if item == 1>
            <#if item?counter == firstUnMaskedInput>
            <input  onkeyup="onPUKCodeKeyUp(${item?counter})" type="text"  id="input_puk_${item?counter}" maxlength="1" autofocus name="input_puk_${item?counter}" type="text" class="form-control code-input-text code-input-text-unmasked" />
            <#else>
            <input type="text"  onkeyup="onPUKCodeKeyUp(${item?counter})"  id="input_puk_${item?counter}" maxlength="1" name="input_puk_${item?counter}" type="text" class="form-control code-input-text code-input-text-unmasked" />
        </#if>

        <#elseif item == 0>
        <input type="text"  class="form-control code-input-text code-input-text-masked" disabled  type="text"  />
        <input   id="input_puk_${item?counter}" name="input_puk_${item?counter}"     type="hidden"  />
    </#if>


</#list>
</div>


</div>







<div id="kc-form-buttons" class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
    <div class="button float-right">
        <input class="btn btn-lg btn-primary" name="login" id="kc-login" type="submit" value="Login"/>
        <input class="btn btn-lg btn-default" name="cancel" id="kc-cancel" type="submit" value="Cancel"/>
    </div>
</div>

</form>

</#if>
</@layout.registrationLayout>
<style type="text/css">
.no-border{
border:0px;
}

.padding-1{
padding:1px;
}
.form-signin-puk{
width:100%;max-width:450px;padding:15px;margin:auto
}
    .code-input-text{
max-width:50px;
height:50px;
text-align:center;
display:inline;
    color:black;
    border:1px solid black;


    }


    .code-input-text-masked{
    background-color:#bbbbb;

    }
    input[type=text]:focus {
    outline: none;
    border: 1px solid blue !important;
    box-shadow: 0 0 5px rgba(81, 203, 238, 1);
  padding: 3px 0px 3px 3px;
  border: 1px solid rgba(81, 203, 238, 1);
}

</style>
<script >


function onPUKCodeKeyUp(currentElementIndex) {

    var currentElem = document.getElementById("input_puk_"+currentElementIndex);

    var allEnabledInputs = Array.from(document.getElementsByClassName("code-input-text-unmasked"));

    if (currentElem.value.length === parseInt(currentElem.attributes["maxlength"].value)) {
    let currentInputIndex = allEnabledInputs.indexOf(currentElem);
      let nextInputIndex;

       nextInputIndex = (currentInputIndex+1)%allEnabledInputs.length;
      if(nextInputIndex < allEnabledInputs.length){
      allEnabledInputs[nextInputIndex].focus();
      }


    }
}


</script>