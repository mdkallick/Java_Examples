-- Quartus II VHDL Template
-- pldROM
library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
entity pldrom is
    port
    (
        addr : in std_logic_vector(3 downto 0);
        data : out std_logic_vector(9 downto 0)
    );
end entity;
architecture rtl of pldrom is
begin
  data <= 
	"0010100111" when addr = "0000" else
	"0011101110" when addr = "0001" else
	"0001000000" when addr = "0010" else
	"0110101111" when addr = "0011" else
	"0110011100" when addr = "0100" else
	"1111111111";
end rtl;