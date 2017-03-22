function verifName(champ)
{
   if(champ.value.length < 3 )
   {
      surligne(champ, true);
      return false;
   }
   else
   {
      surligne(champ, false);
      return true;
   }
}

function verifDate(champ)
{
   var regex = /^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$/;
   if(champ.value.length >= 1 && !regex.test(champ.value))
   {
      surligne(champ, true);
      return false;
   }
   else
   {
      surligne(champ, false);
      return true;
   }
}


function verifForm(f)
{
   var nameOk = verifName(f.computerName);
   var introduceDateOk = verifDate(f.introduce);
   var discontinuedDateOk = verifDate(f.discontinued);
   
   if(nameOk && introduceOk && discontinuedOk)
      return true;
   else
   {
      alert("Veuillez remplir correctement tous les champs");
      return false;
   }
}

function surligne(champ, erreur)
{
   if(erreur)
      champ.style.backgroundColor = "#fba";
   else
      champ.style.backgroundColor = "";
}