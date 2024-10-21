{
  jre,
  makeWrapper,
  maven,
}:
maven.buildMavenPackage rec {
  pname = "chess";
  version = "0.0.1";

  src = ./.;

  mvnHash = "sha256-ZAy0LuHxbWqsANCY2ZQLRoXN6yV+VWO6Dok5wZd9CO0=";

  nativeBuildInputs = [makeWrapper];

  installPhase = ''
    mkdir -p $out/bin $out/share
    mkdir -p $out/share/controlp5 $out/share/processing
    cp -r dist/* $out/share
    cp -r repository/thm/pis/controlp5/${version}/* $out/share/controlp5
    cp -r repository/thm/pis/processing/${version}/* $out/share/processing

    classpath="$out/share/${pname}/${pname}-${version}.jar"
    classpath="$classpath:$out/share/api/api-${version}.jar"
    classpath="$classpath:$out/share/controller/controller-${version}.jar"
    classpath="$classpath:$out/share/model/model-${version}.jar"
    classpath="$classpath:$out/share/view/view-${version}.jar"
    classpath="$classpath:$out/share/controlp5/controlp5-${version}.jar"
    classpath="$classpath:$out/share/processing/processing-${version}.jar"

    makeWrapper ${jre}/bin/java $out/bin/${pname} \
      --add-flags "-cp $classpath chess.Chess"
  '';
}
