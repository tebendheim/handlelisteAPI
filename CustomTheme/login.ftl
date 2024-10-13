
<#import "template.ftl" as layout>

    <@layout.registrationLayout displayMessage=!messagesPerField.existsError('username','password') displayInfo=realm.password && realm.registrationAllowed && !registrationDisabled??; section>


        <#if section = "header">
        <div class="mb-10 text-center">
            <link rel="stylesheet" href="https://horizon-ui.com/shadcn-nextjs-boilerplate/_next/static/css/32144b924e2aa5af.css" />
            ${msg("loginAccountTitle")}
            </div>
        <#elseif section = "form">

        <!-- Dette er øverste from wrapper -->
        <!-- kan lage header her? -->
            <div id="kc-form" class="w-full m-auto">

            <div id="kc-header" class="${properties.kcHeaderClass!} flex items-center justify-center">
              <div id="kc-form-wrapper" class="bg-white shadow-md rounded p-4">
                <#if realm.password>
                    <form id="kc-form-login" onsubmit="login.disabled = true; return true;" action="${url.loginAction}" method="post">
                        <#if !usernameHidden??>
                            <div class="mb-4">
                                <label for="username" class="block text-gray-700 text-sm font-bold mb-2">
                                    <#if !realm.loginWithEmailAllowed>${msg("username")}<#elseif !realm.registrationEmailAsUsername>${msg("usernameOrEmail")}<#else>${msg("email")}</#if>
                                </label>
                                <input tabindex="2" id="username" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" name="username" value="${(login.username!'')}" type="text" autofocus autocomplete="username"
                                       aria-invalid="<#if messagesPerField.existsError('username','password')>true</#if>" />
                                <#if messagesPerField.existsError('username','password')>
                                    <span id="input-error" class="text-red-500 text-xs italic" aria-live="polite">
                                        ${kcSanitize(messagesPerField.getFirstError('username','password'))?no_esc}
                                    </span>
                                </#if>
                            </div>
                        </#if>

                        <div class="mb-6">
                            <label for="password" class="block text-gray-700 text-sm font-bold mb-2">
                                ${msg("password")}
                            </label>
                            <div class="relative">
                                <input tabindex="3" id="password" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" name="password" type="password" autocomplete="current-password"
                                       aria-invalid="<#if messagesPerField.existsError('username','password')>true</#if>" />
                                <button class="absolute right-0 top-0 mt-2 mr-4" type="button" aria-label="${msg("showPassword")}"
                                        data-password-toggle tabindex="4"
                                        data-icon-show="${properties.kcFormPasswordVisibilityIconShow!}" data-icon-hide="${properties.kcFormPasswordVisibilityIconHide!}"
                                        data-label-show="${msg('showPassword')}" data-label-hide="${msg('hidePassword')}">
                                    <i class="${properties.kcFormPasswordVisibilityIconShow!}" aria-hidden="true"></i>
                                </button>
                            </div>
                            <#if usernameHidden?? && messagesPerField.existsError('username','password')>
                                <span id="input-error" class="text-red-500 text-xs italic" aria-live="polite">
                                    ${kcSanitize(messagesPerField.getFirstError('username','password'))?no_esc}
                                </span>
                            </#if>
                        </div>

                        <div class="mb-4">
                            <#if realm.rememberMe && !usernameHidden??>
                                <label class="block text-gray-700 text-sm font-bold">
                                    <input tabindex="5" id="rememberMe" name="rememberMe" type="checkbox" class="mr-2 leading-tight">
                                    ${msg("rememberMe")}
                                </label>
                            </#if>
                        </div>

                        <div class="flex flex-col gap-4 mb-4 items-center justify-between md:flex-row">
                            <button tabindex="7" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" type="submit" name="login" id="kc-login">
                                ${msg("doLogIn")}
                            </button>
                            <#if realm.resetPasswordAllowed>
                                <a class="inline-block align-baseline font-bold text-sm text-blue-500 hover:text-blue-800" href="${url.loginResetCredentialsUrl}">
                                    ${msg("doForgotPassword")}
                                </a>
                            </#if>
                        </div>
                    </form>
                </#if>
              </div>
            </div>
            <script type="module" src="${url.resourcesPath}/js/passwordVisibility.js"></script>
        <#elseif section == "info">
            <#if realm.password && realm.registrationAllowed && !registrationDisabled??>
                <div id="kc-registration-container" class="text-center mt-4">
                    <span class="text-sm">${msg("noAccount")} 
                        <a class="text-blue-500 hover:text-blue-800" href="${url.registrationUrl}">
                            ${msg("doRegister")}
                        </a>
                    </span>
                </div>
            </#if>
        <#elseif section == "socialProviders">
            <#if realm.password && social?? && social.providers?has_content>
                <div id="kc-social-providers" class="mt-4">
                    <hr class="my-4">
                    <h2 class="text-center text-lg font-semibold">${msg("identity-provider-login-label")}</h2>
                    <ul class="flex flex-wrap justify-center">
                        <#list social.providers as p>
                            <li class="m-2">
                                <a id="social-${p.alias}" class="bg-white text-gray-800 font-bold py-2 px-4 rounded inline-flex items-center shadow-md hover:bg-gray-100" href="${p.loginUrl}">
                                    <#if p.iconClasses?has_content>
                                        <i class="${properties.kcCommonLogoIdP!} ${p.iconClasses!}" aria-hidden="true"></i>
                                        <span class="ml-2">${p.displayName!}</span>
                                    <#else>
                                        <span>${p.displayName!}</span>
                                    </#if>
                                </a>
                            </li>
                        </#list>
                    </ul>
                </div>
            </#if>
        </#if>
        

    </@layout.registrationLayout>
