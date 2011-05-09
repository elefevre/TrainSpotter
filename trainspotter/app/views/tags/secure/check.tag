#{if session.username && controllers.Secure.check(_arg)}
    #{doBody /}
#{/if}