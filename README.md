# Spring Boot Base Project

Esse projeto tem como objetivo facilitar a construção de exemplos e provas de conceito, para testar possibilidades, implementações, frameworks e etc.

Para executar esse projeto é necessário:

1. Ter o banco de dados MySQL instalado;
2. Criar um usuário identificado por 'softcube' com a senha 'Brasil@77" e com permissões de DBA;
3. Ter um database chamado 'store';

Caso não deseje ter uma instância local do MySQL é possível provisioná-lo usando o Vagrant.

### VM Virtual Box

Faça o download do VM Virtual Box para sua plataforma (Windows/Linux): https://www.virtualbox.org/wiki/Downloads

#### Windows
Execute o instalador e siga os passos informados na tela.

#### Linux 
Execute o comando:

`sudo dpkg -i /path/to/the/vm_virtual_box_package.deb`

### Vagrant
Faça o download do Vagrant para sua plataforma (Windows/Linux): https://www.vagrantup.com/downloads

#### Windows

Execute o instalador e siga os passos informados na tela.

#### Linux

Execute o comando:

`sudo dpkg -i /path/to/the/vagrant_package.deb`

## Configuração do Vagrant para o projeto
O Vagrant é uma ferramenta de virtualização e uma das configurações necessárias para execução do projeto é a liberação de uma porta
na máquina hospedeira para a máquina convidada.

Além disso também é necessário a criação de uma chave privada para realizar a conexão ssh entre a máquina hospedeira e a máquina convidada.

Para isso, siga os passos abaixo: 
1. Crie uma chave do tipo RSA e copie a chave pública para o diretório `resources/vagrant/configs`
2. Entre na pasta `resources` e edite o arquivo chamado `Vagrantfile`
3. Altere o conteúdo entre as cerquílhas # para definir a porta do MySQL    
`config.vm.network "forwarded_port", guest: #guest_port#, host: #host_port#`
4. Altere o conteúdo entre as cerquílhas # para definir o ip da máquina convidada   
`config.vm.network "public_network", ip: "#guest_address#"`
5. Altere o conteúdo entre as cerquílhas # para apontar para sua chave pública  
`config.vm.provision "shell", inline: "cat /configs/#public_key#.pub >> .ssh/authorized_keys"`

Assim como a porta definida em #host_port# e #guest_port#, o endereço de ip definido em #guest_address# precisam estar disponíveis para uso, caso contrário erros ocorrerão.

## Executando o Vagrant
Feito os passos acima com sucesso, agora basta executar o Vagrant. Para isso, vá até a pasta onde está o arquivo `Vagrantfile` e digite:    
`vagrant up`

Se tudo correr como o esperado, a saída abaixo será apresentada no console:     
`default: Creating database store`

Para destruir a máquina virtual e recriá-la basta utilizar o comando abaixo:    
`vagrant destroy -f && vagrant up`