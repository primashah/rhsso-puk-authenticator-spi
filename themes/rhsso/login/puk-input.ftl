<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
    <#if section = "title">
        ${msg("loginTitle",realm.name)}
    <#elseif section = "header">
        ${msg("loginTitleHtml",realm.name)}
    <#elseif section = "form">

<!--
<div class="container-fluid">
    <input type="text" class="col-md-2" autofocus style="margin: 0 3px;border:0px;border-bottom:1px solid blue"/>
    <input type="text" class="col-md-2" style="margin: 0 3px;"/>
    <input type="text" class="col-md-2" style="margin: 0 3px;"/>
    <input type="text" class="col-md-2" style="margin: 0 3px;"/>
    <input type="text" class="col-md-2" style="margin: 0 3px;" disabled/>
    <input type="text" class="col-md-2" style="margin: 0 3px;"/>
    <input type="text" class="col-md-2" style="margin: 0 3px;"/>
</div>
-->


            <form id="kc-totp-login-form" class="${properties.kcFormClass!}" action="${url.loginAction}" method="post">
                <div class="${properties.kcFormGroupClass!}">
                    <div class="col-xs-12 col-sm-12 col-md-4 col-lg-8">
                        <label  class="${properties.kcLabelClass!}">Please enter PUK number</label>


                        <div style="width:100%">



                                    <#list inputArray as item>


                                    <#if item == 1>
                                        <#if item?counter == firstUnMaskedInput>
                                             <input  onkeyup="onPUKCodeKeyUp(${item?counter})" type="text"  id="input_puk_${item?counter}" maxlength="1" autofocus name="input_puk_${item?counter}" type="text" class="code-input-text code-input-text-unmasked" />
                                        <#else>
                                        <input type="text"  onkeyup="onPUKCodeKeyUp(${item?counter})"  id="input_puk_${item?counter}" maxlength="1" name="input_puk_${item?counter}" type="text" class="code-input-text code-input-text-unmasked" />
                                        </#if>

                                    <#elseif item == 0>
                                    <input type="text"  class="code-input-text code-input-text-masked" disabled  type="text"  />
                            <input   id="input_puk_${item?counter}" name="input_puk_${item?counter}"     type="hidden"  />
                                     </#if>


                                </#list>


                    </div>

                    </div>
</div>
                <div class="${properties.kcFormGroupClass!}">



                    <div id="kc-form-buttons" class="col-xs-8 col-sm-7 col-md-4 col-lg-8 submit">
                        <div class="${properties.kcFormButtonsWrapperClass!}">
                            <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonLargeClass!}" name="login" id="kc-login" type="submit" value="${msg("doLogIn")}"/>
                            <input class="${properties.kcButtonClass!} ${properties.kcButtonDefaultClass!} ${properties.kcButtonLargeClass!}" name="cancel" id="kc-cancel" type="submit" value="${msg("doCancel")}"/>
                        </div>
                    </div>
                </div>

            </form>

    </#if>
</@layout.registrationLayout>
<style type="text/css">
    .code-input-text{
max-width:50px;
height:50px;
text-align:center;

    color:black;
    border:1px solid black;


    }


    .code-input-text-masked{
    background-color:#bbb;

    }
    input[type=text]:focus {
    outline: none;
    border: 1px solid blue !important;
    box-shadow: 0 0 5px rgba(81, 203, 238, 1);
  padding: 3px 0px 3px 3px;
  border: 1px solid rgba(81, 203, 238, 1);
}

</style>
<script>


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