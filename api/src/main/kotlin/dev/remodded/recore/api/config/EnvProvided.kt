package dev.remodded.recore.api.config

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class EnvProvided(val envName: String)
