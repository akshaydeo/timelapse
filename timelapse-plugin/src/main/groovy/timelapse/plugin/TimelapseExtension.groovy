package timelapse.plugin;

class TimelapseExtension {
  def enabled = true
  def packageName = ""

  def setEnabled(boolean enabled) {
    this.enabled = enabled
  }

  def getEnabled() {
    return enabled;
  }

  def setPackageName(String packageName) {
    this.packageName = packageName;
  }

  def getPackageName() {
    return this.packageName;
  }
}