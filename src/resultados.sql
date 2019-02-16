CREATE OR REPLACE FUNCTION set_dominados()
  RETURNS void AS
$BODY$
DECLARE
  v_entropia    resultados.entropia%TYPE;
  v_contraste   resultados.contraste%TYPE;
  v_id  	resultados.id%TYPE;
  v_nombre	resultados.nombre%TYPE;
  v_fila RECORD;
  cant_dominantes int;
BEGIN
  -- Assign to bind variables before the cursor OPEN.
	v_nombre := '%mdb001.pgm%';
	FOR v_fila IN SELECT * FROM resultados WHERE nombre like v_nombre LOOP
		cant_dominantes=0;
		SELECT count(*) FROM resultados where contraste > v_fila.contraste and entropia > v_fila.entropia
		  and nombre like v_nombre and dominado='N' INTO cant_dominantes;
		 if (cant_dominantes > 0) then
			update resultados set dominado='S' where id=v_fila.id;
		 end if;
	END LOOP;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION set_dominados()
  OWNER TO postgres;