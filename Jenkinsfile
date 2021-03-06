parallel portalbranch: {
	node {
		env.JAVA_HOME="${tool 'jdk7'}"
		env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
		def mvnHome
		mvnHome = tool 'maven3'	
		stage("osivia-portal") {
		    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'osivia-portal', remote: 'http://www.osivia.org/repos/osivia-portal/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		    
		    sh "'${mvnHome}/bin/mvn' clean deploy -U -Punpack,pack -f osivia-portal"
		}
		stage("toutatice-cms") {
		    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'toutatice-cms', remote: 'http://projet.toutatice.fr/repos/toutatice-portail/cms/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		    
		    sh "'${mvnHome}/bin/mvn' clean deploy -U -Punpack,pack -f toutatice-cms"
		}
		stage("osivia-directory") {
		    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'osivia-directory', remote: 'http://www.osivia.org/repos/osivia-services/directory/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		    
		    sh "'${mvnHome}/bin/mvn' clean deploy -U -f osivia-directory"
		}
		stage("osivia-collaboration") {
		    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'osivia-collaboration', remote: 'http://www.osivia.org/repos/osivia-services/collaboration/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		    
		    sh "'${mvnHome}/bin/mvn' clean deploy -U -f osivia-collaboration"
		}
		stage("osivia-procedures") {
		    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'osivia-procedures', remote: 'http://www.osivia.org/repos/osivia-services/procedure/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		    
		    sh "'${mvnHome}/bin/mvn' clean deploy -U -f osivia-procedures"
		}
		stage("osivia-tasks") {
		    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'osivia-tasks', remote: 'http://www.osivia.org/repos/osivia-services/tasks/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		    
		    sh "'${mvnHome}/bin/mvn' clean deploy -U -f osivia-tasks"
		}
		stage("cgu") {
		    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'cgu', remote: 'http://www.osivia.org/repos/osivia-services/cgu/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		    
		    sh "'${mvnHome}/bin/mvn' clean deploy -U -f cgu"
		}		
		stage("demo-distribution") {
		    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'demo', remote: 'http://www.osivia.org/repos/osivia-demo/demo/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		    
		    sh "'${mvnHome}/bin/mvn' clean deploy -U -f demo -Punpack,pack"
		}
	}
}, nuxeobranch : {
	node {
		env.JAVA_HOME="${tool 'jdk7'}"
		env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
		def mvnHome
		mvnHome = tool 'maven3'		
		stage("opentoutatice") {
		    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'opentoutatice', remote: 'http://projet.toutatice.fr/repos/toutatice-ecm/opentoutatice/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		    
		    sh "'${mvnHome}/bin/mvn' clean deploy -U -Dmaven.test.skip=true -f opentoutatice"
		}
		
		stage("elasticsearch") {
		    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'elasticsearch', remote: 'http://projet.toutatice.fr/repos/toutatice-ecm/opentoutatice-addons/opentoutatice-elasticsearch/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		    
		    sh "'${mvnHome}/bin/mvn' clean deploy -U -f elasticsearch"
		}		
		stage("es-customizer") {
		    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'es-customizer', remote: 'http://projet.toutatice.fr/repos/toutatice-ecm/opentoutatice-addons/opentoutatice-elasticsearch-customizer/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		    
		    sh "'${mvnHome}/bin/mvn' clean deploy -U -f es-customizer"
		}		
		stage("ecm-procedures") {
		    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'ecm-procedures', remote: 'http://projet.toutatice.fr/repos/toutatice-ecm/opentoutatice-addons/opentoutatice-procedures/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		    
		    sh "'${mvnHome}/bin/mvn' clean deploy -U -f ecm-procedures"
		}		

		stage("collab-tools") {
		    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'collab-tools', remote: 'http://projet.toutatice.fr/repos/toutatice-ecm/opentoutatice-addons/opentoutatice-collab-tools/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		    
		    sh "'${mvnHome}/bin/mvn' clean deploy -U -f collab-tools"
		}		
		stage("file-versioning") {
            checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'file-versioning', remote: 'http://projet.toutatice.fr/repos/toutatice-ecm/opentoutatice-addons/opentoutatice-file-versioning/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
            
            sh "'${mvnHome}/bin/mvn' clean deploy -U -f file-versioning"
        }	

		//stage("wf-integration") {
		//   checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'wf-integration', remote: 'http://projet.toutatice.fr/repos/toutatice-ecm/opentoutatice-addons/opentoutatice-workflows-integration/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		//    
		//    sh "'${mvnHome}/bin/mvn' clean deploy -U -f wf-integration"
		//}
        
		stage("notifications") {
		    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'notifications', remote: 'http://projet.toutatice.fr/repos/toutatice-ecm/opentoutatice-addons/opentoutatice-portal-notifications/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		    
		    sh "'${mvnHome}/bin/mvn' clean deploy -U -f notifications"
		}        
        
		//stage("checkin") {
		//    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'checkin', remote: 'http://projet.toutatice.fr/repos/toutatice-ecm/opentoutatice-addons/opentoutatice-checkin/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		//    
		//    sh "'${mvnHome}/bin/mvn' clean deploy -U -f checkin"
		//}
		//stage("simpleui") {
		//    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'simpleui', remote: 'http://projet.toutatice.fr/repos/toutatice-ecm/opentoutatice-addons/opentoutatice-simple-ui/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		//    
		//    sh "'${mvnHome}/bin/mvn' clean deploy -U -f simpleui"
		//}
		
		//stage("file-naming") {
		//    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'file-naming', remote: 'http://projet.toutatice.fr/repos/toutatice-ecm/opentoutatice-addons/opentoutatice-file-based-naming/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		//    
		//    sh "'${mvnHome}/bin/mvn' clean deploy -U -f file-naming"
		//}

		//stage("ottc-news") {
		//    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'ottc-news', remote: 'http://projet.toutatice.fr/repos/toutatice-ecm/opentoutatice-addons/opentoutatice-news/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		//    
		//    sh "'${mvnHome}/bin/mvn' clean deploy -U -f ottc-news"
		//}		
		//stage("drive") {
		//	checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'drive', remote: 'http://projet.toutatice.fr/repos/toutatice-ecm/opentoutatice-addons/opentoutatice-drive/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		//	sh "'${mvnHome}/bin/mvn' clean deploy -U -f drive"
		//}
		stage("nx-demo") {
		    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'nx-demo', remote: 'http://www.osivia.org/repos/osivia-demo/demo/nuxeo/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		    sh "'${mvnHome}/bin/mvn' clean deploy -U -f nx-demo"
		}
		stage("webmode") {
		    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'webmode', remote: 'http://projet.toutatice.fr/repos/toutatice-ecm/opentoutatice-addons/opentoutatice-web-mode/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		    sh "'${mvnHome}/bin/mvn' clean deploy -U -f webmode"
		}
		stage("nx-distrib") {
		    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '', depthOption: 'infinity', ignoreExternalsOption: true, local: 'nx-distrib', remote: 'http://www.osivia.org/repos/osivia-services/nx-distributions/demo/trunk']], workspaceUpdater: [$class: 'UpdateUpdater']])
		    sh "'${mvnHome}/bin/mvn' clean deploy -U -f nx-distrib"
		}
	}
}


