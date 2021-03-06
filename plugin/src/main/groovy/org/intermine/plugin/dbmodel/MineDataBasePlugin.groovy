package org.intermine.plugin.dbmodel

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.intermine.plugin.VersionConfig

class MineDataBasePlugin implements Plugin<Project> {

    VersionConfig mineVersionConfig

    void apply(Project project) {
        String projectXmlFilePath = project.getParent().getProjectDir().getAbsolutePath() + File.separator + "project.xml"
        mineVersionConfig = project.extensions.create('mineVersionConfig', VersionConfig)

        project.configurations {
            mergeSource
        }

        project.task('parseProjectXml') {
            description "Parse the project XML file and add associated datasource dependencies"
            onlyIf {!new File(project.getBuildDir().getAbsolutePath() + File.separator + "gen").exists()}

            doLast {
                def projectXml = (new XmlParser()).parse(projectXmlFilePath)
                projectXml.sources.source.each { source ->
                    project.dependencies.add("mergeSource", [group: "org.intermine", name: "bio-source-" + "${source.'@type'}", version: mineVersionConfig.bioSourceVersion])
                }
            }
        }

        project.task('mergeModels') {
            description "Merges defferent source model files into an intermine XML model"
            dependsOn 'initConfig', 'copyGenomicModel', 'copyMineProperties', 'createSoModel', 'parseProjectXml'
            onlyIf {!new File(project.getBuildDir().getAbsolutePath() + File.separator + "gen").exists()}

            MineDBConfig config = project.extensions.create('mineDBConfig', MineDBConfig)

            doLast {
                SourceSetContainer sourceSets = (SourceSetContainer) project.getProperties().get("sourceSets");
                String buildResourcesMainDir = sourceSets.getByName("main").getOutput().resourcesDir;
                def ant = new AntBuilder()
                //added this dependecy otherwise ant doesn't found MergeSourceModelsTask at runtime
                project.dependencies.add("mergeSource", [group: "org.intermine", name: "plugin", version: "1.+"])
                String modelFilePath = buildResourcesMainDir + File.separator + config.modelName + "_model.xml"
                ant.taskdef(name: "mergeSourceModels", classname: "org.intermine.plugin.ant.MergeSourceModelsTask") {
                    classpath {
                        pathelement(path: project.configurations.getByName("mergeSource").asPath)
                        pathelement(path: project.configurations.getByName("compile").asPath)
                        dirset(dir: project.getBuildDir().getAbsolutePath())
                    }
                }
                ant.mergeSourceModels(projectXmlPath: projectXmlFilePath,
                        modelFilePath: modelFilePath,
                        extraModelsStart: config.extraModelsStart,
                        extraModelsEnd: config.extraModelsEnd)
            }
        }
    }


}

