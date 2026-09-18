{
  description = "Development shell for the Bigger Barrels Fabric mod";

  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";

  outputs = { self, nixpkgs }:
    let
      systems = [ "x86_64-linux" "aarch64-linux" ];
      forAllSystems = nixpkgs.lib.genAttrs systems;
    in {
      devShells = forAllSystems (system:
        let pkgs = import nixpkgs { inherit system; };
        in {
          default = pkgs.mkShell {
            packages = [ pkgs.jdk25 pkgs.gradle_9 pkgs.git pkgs.unzip ];
            JAVA_HOME = "${pkgs.jdk25}/lib/openjdk";
          };
        });
    };
}
