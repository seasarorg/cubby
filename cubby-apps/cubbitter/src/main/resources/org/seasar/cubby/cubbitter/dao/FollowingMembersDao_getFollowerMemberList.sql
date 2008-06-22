SELECT m.member_id, m.member_name, m.full_name, fm2.request 
FROM following_members fm1
INNER JOIN member m 
ON fm1.member_id = m.member_id
LEFT OUTER JOIN following_members fm2
ON fm2.member_id = fm1.following_member_id
AND fm2.following_member_id = fm1.member_id
WHERE fm1.following_member_id = /*memberId*/3
AND fm1.request = false
ORDER BY m.member_id;