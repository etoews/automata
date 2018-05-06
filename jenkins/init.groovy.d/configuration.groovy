#!groovy

import jenkins.model.Jenkins
import hudson.security.csrf.DefaultCrumbIssuer
import jenkins.security.s2m.AdminWhitelistRule

def instance = Jenkins.getInstance()

// Disable remoting
instance.getDescriptor("jenkins.CLI").get().setEnabled(false)

// Enable Agent to master security subsystem
instance.injector.getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false);

// Disable jnlp
instance.setSlaveAgentPort(-1);

//  CSRF Protection
instance.setCrumbIssuer(new DefaultCrumbIssuer(true))

// Disable old Non-Encrypted protocols
HashSet<String> newProtocols = new HashSet<>(instance.getAgentProtocols());
newProtocols.removeAll(Arrays.asList(
    "JNLP3-connect", "JNLP2-connect", "JNLP-connect", "CLI-connect"
));
instance.setAgentProtocols(newProtocols);

instance.save()
